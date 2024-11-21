package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.application.port.outbound.LoadGroupMemberPort;
import com.phishing.notiservice.domain.GroupMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupMemberPersistenceAdapter implements LoadGroupMemberPort {
    private final GroupMemberRepository groupMemberRepository;

    @Override
    public List<GroupMember> loadGroupMemberByGroupId(Long groupId) {
        // Load group member from database
        List<GroupMemberEntity> result = groupMemberRepository.findAllByGroup(groupId);
        return result.stream()
                .map(GroupMemberMapper::toDomain)
                .toList();
    }

}
