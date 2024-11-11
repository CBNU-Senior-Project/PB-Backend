package com.phishing.userservice.domain.group.payload.request;

import jakarta.validation.constraints.Pattern;

public record InviteMemberRequest(
        @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
        String receiverPhoneNumber

) {}
