package com.phishing.notiservice.adapter.inbound.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phishing.common.payload.Passport;
import com.phishing.notiservice.application.port.inbound.*;
import com.phishing.notiservice.domain.DeviceInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/noti/api/v1")
@RequiredArgsConstructor
@Validated
public class NotiController {

    private final ObjectMapper objectMapper;
    private final RegisterUserUsecase registerUserUsecase;
    private final ViewNotiListUsecase viewNotiListUsecase;

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

    @GetMapping("/list")
    public ResponseEntity<List<ViewNotiListResponse>> viewNotiList(
            @RequestHeader("X-Authorization") String passport) throws JsonProcessingException {
        Passport userInfo = objectMapper.readValue(passport, Passport.class);
        ViewNotiListQuery viewNotiListQuery = new ViewNotiListQuery(userInfo.userId());
        return ResponseEntity.ok(viewNotiListUsecase.viewNotiList(viewNotiListQuery));
    }
}
