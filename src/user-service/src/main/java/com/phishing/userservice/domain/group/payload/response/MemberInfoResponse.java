package com.phishing.userservice.domain.group.payload.response;

public class MemberInfoResponse {
    private Long userId;
    private String name;
    private String phnum;
    private String imageUrl; // 추가된 필드

    public MemberInfoResponse(Long userId, String name, String phnum,  String imageUrl) {
        this.userId = userId;
        this.name = name;
        this.phnum = phnum;
        this.imageUrl = imageUrl; // 초기화
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhnum() {
        return phnum;
    }



    public String getImageUrl() { // 추가된 getter
        return imageUrl;
    }
}
