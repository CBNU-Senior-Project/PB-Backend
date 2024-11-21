package com.phishing.notiservice.application.port.outbound;

import com.phishing.notiservice.domain.GroupMember;

import java.util.List;

public interface LoadGroupMemberPort {
    List<GroupMember> loadGroupMemberByGroupId(Long groupId);
}
