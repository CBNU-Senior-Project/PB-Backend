package com.phishing.userservice.domain.group.payload.response;

public class MemberInfoResponse {
    private Long userId;
    private String name;
    private String phnum;

    public MemberInfoResponse(Long userId, String name, String phnum) {
        this.userId = userId;
        this.name = name;
        this.phnum = phnum;
    }

    // Getters and setters, if needed
    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhnum() {
        return phnum;
    }
}
