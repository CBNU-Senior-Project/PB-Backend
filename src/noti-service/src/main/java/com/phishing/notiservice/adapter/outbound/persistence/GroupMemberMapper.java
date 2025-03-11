package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.domain.GroupMember;
import org.springframework.stereotype.Component;

@Component
public class GroupMemberMapper {
    public static GroupMember toDomain(GroupMemberEntity groupMemberEntity) {
        return GroupMember.createGroupMember(
                groupMemberEntity.getGroup(),
                groupMemberEntity.getUser(),
                groupMemberEntity.isAdmin(),
                groupMemberEntity.getNickname(),
                groupMemberEntity.getImagename()
        );
    }
}
