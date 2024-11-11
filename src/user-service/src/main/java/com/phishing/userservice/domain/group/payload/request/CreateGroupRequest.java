package com.phishing.userservice.domain.group.payload.request;

import jakarta.validation.constraints.Pattern;

public record CreateGroupRequest(
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,15}$")
        String name

) {}
