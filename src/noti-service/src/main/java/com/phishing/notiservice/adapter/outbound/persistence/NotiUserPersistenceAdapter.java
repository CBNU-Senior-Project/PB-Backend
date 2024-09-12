package com.phishing.notiservice.adapter.outbound.persistence;

import com.phishing.notiservice.application.port.outbound.LoadNotiUserPort;
import com.phishing.notiservice.application.port.outbound.SaveNotiUserPort;
import com.phishing.notiservice.domain.NotiUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotiUserPersistenceAdapter implements LoadNotiUserPort, SaveNotiUserPort {

    private final NotiUserRepository notiUserRepository;
    @Override
    public NotiUser loadNotiUser(Long userId) {
        return NotiUserMapper.toDomain(notiUserRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new RuntimeException("Noti User not found")));
    }
    @Override
    public List<NotiUser> loadNotiUserByGroupId(Long groupId){
        return notiUserRepository.findByGroupIdAndIsDeletedFalse(groupId).stream()
                .map(NotiUserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void saveNotiUser(NotiUser notiUser) {
        notiUserRepository.save(NotiUserMapper.toEntity(notiUser));
    }
}
