package com.phishing.notiservice.domain;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class GroupMember {

    private Long groupMemberId;
    private Long group;
    private Long user;
    private boolean isAdmin;
    private String nickname;
    private String imagename;

    public static GroupMember createGroupMember(Long group, Long user, boolean isAdmin, String nickname, String imagename) {
        return GroupMember.builder()
                .group(group)
                .user(user)
                .isAdmin(isAdmin)
                .nickname(nickname)
                .imagename(imagename)
                .build();
    }

}
