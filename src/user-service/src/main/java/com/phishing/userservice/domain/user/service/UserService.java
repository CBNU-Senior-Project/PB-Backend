package com.phishing.userservice.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phishing.common.payload.Passport;
import com.phishing.userservice.domain.user.domain.User;
import com.phishing.userservice.domain.user.exception.exceptions.DuplicateEmailException;
import com.phishing.userservice.domain.user.payload.request.EditProfileRequest;
import com.phishing.userservice.domain.user.payload.request.SignUpRequest;
import com.phishing.userservice.domain.user.payload.response.UserIdResponse;
import com.phishing.userservice.domain.user.payload.response.UserInfoResponse;
import com.phishing.userservice.domain.user.repository.UserRepository;

import com.phishing.userservice.domain.user.util.UserInfoConverter;
import com.phishing.userservice.domain.user.util.UserResponseConverter;
import com.phishing.userservice.global.component.token.TokenResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenResolver tokenResolver;
    private final ObjectMapper objectMapper;

    public void checkEmail(String email) {
        if (userRepository.existsByUserCertification_Email(email)) {
            throw new DuplicateEmailException("Email already exists");
        }
    }

    public void signUp(SignUpRequest request) {
        checkEmail(request.userCertification().getEmail());

        // 랜덤 인증 코드 생성
        String verificationCode = String.valueOf(new Random().nextInt(999999));

        // 인증 코드 전송
        twilioService.sendVerificationCode(request.userInfo().getPhnum(), verificationCode);

        User user = User.signUp(
                request.userCertification().getEmail(),
                passwordEncoder.encode(request.userCertification().getPassword()),
                request.userInfo(),
                request.userRole()
        );

        // 여기서 인증 코드 검증 로직을 추가할 수 있습니다.

        userRepository.save(user);
    }
    public void editProfile(String token, EditProfileRequest request) throws JsonProcessingException {
        Passport userInfo = objectMapper.readValue(token, Passport.class);
        User targetUser = userRepository.findByUserIdAndIsDeletedIsFalse(userInfo.userId())
                .orElseThrow(() -> new NoSuchElementException("Email Not exists"));

        targetUser.updateUserInfo(request.userInfo());

    }

    public UserInfoResponse viewUserProfile(String passport) throws JsonProcessingException {
        Passport userInfo = objectMapper.readValue(passport, Passport.class);
        User targetUser = userRepository.findByUserIdAndIsDeletedIsFalse(userInfo.userId())
                .orElseThrow(() -> new NoSuchElementException("Email Not exists"));
        return UserInfoConverter.from(targetUser);
    }

    public void resignUser(String token) throws JsonProcessingException {
        Passport userInfo = objectMapper.readValue(token, Passport.class);
        User targetUser = userRepository.findByUserIdAndIsDeletedIsFalse(userInfo.userId())
                .orElseThrow(() -> new NoSuchElementException("Email Not exists"));
        targetUser.resignUser();
    }

    public UserInfoResponse viewUserName(Long userId){
        User targetUser = userRepository.findByUserIdAndIsDeletedIsFalse(userId)
                .orElseThrow(() -> new NoSuchElementException("Email Not exists"));
        return UserInfoConverter.from(targetUser);
    }

    public UserIdResponse findUserIdByPhoneNumber(String phoneNumber) {
        User targetUser = userRepository.findByUserInfo_PhnumAndIsDeletedIsFalse(phoneNumber)
                .orElseThrow(() -> new NoSuchElementException("Phone number not found"));
        return UserResponseConverter.from(targetUser);
    }


}
