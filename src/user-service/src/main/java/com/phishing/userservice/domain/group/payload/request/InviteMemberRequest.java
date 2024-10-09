package com.phishing.userservice.domain.group.payload.request;

public record InviteMemberRequest(
        String receiverPhoneNumber
        // 초대받을 유저의 ID
) {}
