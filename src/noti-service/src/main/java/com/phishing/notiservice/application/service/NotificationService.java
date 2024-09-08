package com.phishing.notiservice.application.service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.phishing.common.event.events.PredictFinishedEvent;
import com.phishing.notiservice.application.port.inbound.NotificationUsecase;
import com.phishing.notiservice.application.port.outbound.LoadNotiUserPort;
import com.phishing.notiservice.application.port.outbound.SaveNotiTrackingPort;
import com.phishing.notiservice.application.port.outbound.SaveNotificationPort;
import com.phishing.notiservice.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService implements NotificationUsecase {

    private final LoadNotiUserPort loadNotiUserPort;
    private final SaveNotificationPort saveNotificationPort;
    private final SaveNotiTrackingPort saveNotiTrackingPort;

    @Transactional
    @Override
    public void sendNotification(PredictFinishedEvent predictFinishedEvent) {
        log.info("Send notification to user: {}, isPhishing: {}, probability: {}",
                predictFinishedEvent.getUserId(), predictFinishedEvent.isPhishing(), predictFinishedEvent.getProbability());
        Notification targetNoti = Notification.create(NotiPayload.createPredFinNoti(predictFinishedEvent.getProbability()), NotiType.POTENTIAL_PHISHING_ALERT,
                loadNotiUserPort.loadNotiUser(predictFinishedEvent.getUserId()).getGroupId());
        saveNotificationPort.saveNotification(targetNoti);
        List<NotiUser> targetUsers = loadNotiUserPort.loadNotiUserByGroupId(targetNoti.getTargetGroupId());
        List<Message> messages = new ArrayList<>();
        List<NotiTracking> trackings = new ArrayList<>();
        for(NotiUser user : targetUsers) {
            Message targetMessage =  Message.builder()
                    .setNotification(createNotification(targetNoti.getPayload().getTitle(), targetNoti.getPayload().getMessage()))
                    .setToken(user.getDeviceInfo().getToken())
                    .putData("userId", user.getUserId().toString())
                    .build();
            messages.add(targetMessage);
        }
        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendEach(messages);
            log.info("Successfully sent message: {}", response.getSuccessCount());
            for (NotiUser user : targetUsers) {
                NotiTracking tracking = NotiTracking.create(user.getUserId(),
                        targetNoti.getNotificationId(), NotiStatus.SUCCESS);
                trackings.add(tracking);
            }
        } catch (Exception e) {
            log.error("Failed to send notification: {}", e.getMessage());
            for (NotiUser user : targetUsers) {
                NotiTracking tracking = NotiTracking.create(user.getUserId(),
                        targetNoti.getNotificationId(), NotiStatus.FAIL);
                trackings.add(tracking);
            }
        }

        saveNotiTrackingPort.saveNotiTracking(trackings);
    }

    private com.google.firebase.messaging.Notification createNotification(String title, String body) {
        com.google.firebase.messaging.Notification notification =
                com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build();

        return notification;
    }
}
