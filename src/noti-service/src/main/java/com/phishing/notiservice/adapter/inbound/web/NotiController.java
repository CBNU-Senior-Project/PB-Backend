package com.phishing.notiservice.adapter.inbound.web;

import com.phishing.notiservice.application.port.inbound.RegisterUserCommand;
import com.phishing.notiservice.application.port.inbound.RegisterUserUsecase;
import com.phishing.notiservice.application.port.inbound.SendNotificationUsecase;
import com.phishing.notiservice.domain.DeviceInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/noti/api/v1")
@RequiredArgsConstructor
@Validated
public class NotiController {

    private final RegisterUserUsecase registerUserUsecase;

    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(
                registerUserRequest.userId(),
                registerUserRequest.groupId(),
                new DeviceInfo(
                        registerUserRequest.deviceInfo().getToken(),
                        registerUserRequest.deviceInfo().getDeviceType()
                ),
                registerUserRequest.isNotiEnabled()
        );
        registerUserUsecase.registerNotiUser(registerUserCommand);
    }
}
