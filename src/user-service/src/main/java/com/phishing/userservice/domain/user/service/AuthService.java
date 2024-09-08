package com.phishing.userservice.domain.user.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phishing.common.payload.Passport;
import com.phishing.userservice.domain.user.domain.User;
import com.phishing.userservice.domain.user.exception.exceptions.InvalidPasswordException;
import com.phishing.userservice.domain.user.payload.request.SignInRequest;
import com.phishing.userservice.domain.user.repository.UserRepository;

import com.phishing.userservice.global.component.passport.PassportGenerator;
import com.phishing.userservice.global.component.token.ReturnToken;
import com.phishing.userservice.global.component.token.TokenProvider;
import com.phishing.userservice.global.component.token.TokenResolver;
import com.phishing.userservice.global.redis.RedisDao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RedisDao redisDao;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final TokenResolver tokenResolver;
    private final PassportGenerator passportGenerator;
    private final ObjectMapper objectMapper;

    @Value("${jwt.secret.refresh}")
    private Long refreshTime;

    private static final String ACCESS_TOKEN_HEADER = "Authorization";
    private static final String REFRESH_TOKEN_HEADER = "RefreshToken";

    public ReturnToken signIn(SignInRequest request) {
        userRepository.existsByUserCertification_Email(request.userCertification().getEmail());
        User loginUser = userRepository.findByUserCertification_EmailAndIsDeletedIsFalse(request.userCertification().getEmail())
                .filter(user -> passwordEncoder.matches(request.userCertification().getPassword(), user.getUserCertification().getPassword()))
                .orElseThrow(() -> new InvalidPasswordException("Invalid password"));
        ReturnToken returnToken = tokenProvider.provideTokens(loginUser);
        redisDao.setRedisValues(loginUser.getUserCertification().getEmail(),
                returnToken.refreshToken(), Duration.ofMillis(refreshTime));

        return returnToken;
    }

    public void signOut(HttpServletRequest request) {
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);
        long remainTime = tokenResolver.getExpiration(refreshToken);
        long ttl = remainTime - System.currentTimeMillis();
        Long userId = tokenResolver.getAccessClaims(accessToken);
        if (redisDao.isExistKey(String.valueOf(userId))) {
            redisDao.deleteRedisValues(String.valueOf(userId));
        }
        redisDao.setRedisValues("Blacklist_" + userId, refreshToken, Duration.ofMillis(ttl));
    }

    public ReturnToken refresh(HttpServletRequest request) {
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);
        Long userId = tokenResolver.getRefreshClaims(refreshToken);
        if (!redisDao.isExistKey(String.valueOf(userId)) || !redisDao.getRedisValues(String.valueOf(userId)).equals(refreshToken)) {
            throw new InvalidPasswordException("Invalid refresh token");
        }
        User loginUser = userRepository.findByUserIdAndIsDeletedIsFalse(userId)
                .orElseThrow(() -> new InvalidPasswordException("Invalid refresh token"));
        ReturnToken returnToken = tokenProvider.provideTokens(loginUser);
        redisDao.setRedisValues(loginUser.getUserCertification().getEmail(),
                returnToken.refreshToken(), Duration.ofMillis(refreshTime));

        return returnToken;
    }

    public String generatePassport(HttpServletRequest request) throws JsonProcessingException {
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        Long userId = tokenResolver.getAccessClaims(accessToken);
        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId)
                .orElseThrow(() -> new InvalidPasswordException("Invalid access token"));
        Passport result = passportGenerator.generatePassport(user);
        log.debug("Passport: {}", result);
        String passport = objectMapper.writeValueAsString(result);
        return passport;
    }

}
