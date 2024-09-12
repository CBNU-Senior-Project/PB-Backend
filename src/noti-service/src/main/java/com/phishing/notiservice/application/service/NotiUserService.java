package com.phishing.notiservice.application.service;

import com.phishing.notiservice.application.port.inbound.RegisterUserCommand;
import com.phishing.notiservice.application.port.inbound.RegisterUserUsecase;
import com.phishing.notiservice.application.port.outbound.SaveNotiUserPort;
import com.phishing.notiservice.domain.NotiUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotiUserService implements RegisterUserUsecase {

    private final SaveNotiUserPort saveNotiUserPort;

    @Transactional
    @Override
    public void registerNotiUser(RegisterUserCommand registerUserCommand) {
        NotiUser notiUser = NotiUser.create(
                registerUserCommand.userId(),
                registerUserCommand.groupId(),
                registerUserCommand.deviceInfo(),
                registerUserCommand.isNotiEnabled()
        );
        log.info("Register noti user: {}", notiUser.getUserId());
        saveNotiUserPort.saveNotiUser(notiUser);
    }
}
