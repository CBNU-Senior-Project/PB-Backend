package com.phishing.userservice.global.component.passport;

import com.phishing.common.payload.Passport;
import com.phishing.userservice.domain.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class PassportGenerator {
    public Passport generatePassport(User user){
        return new Passport(
                user.getUserId(),
                user.getUserCertification().getEmail(),
                user.getUserInfo().getNickname(),
                user.getUserRole().toString()
        );
    }
}
