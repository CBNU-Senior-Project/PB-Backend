package com.phishing.userservice.domain.user.payload.response;

import lombok.Builder;

@Builder
public record UserIdResponse(
        Long userId
) {
}
