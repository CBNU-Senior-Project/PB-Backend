package com.phishing.userservice.domain.phish.service;

import com.phishing.userservice.domain.phish.domain.Phishing;
import com.phishing.userservice.domain.phish.domain.PhishingType;
import com.phishing.userservice.domain.phish.payload.response.PhishingResponse;
import com.phishing.userservice.domain.phish.payload.response.SearchPhishingResponse;
import com.phishing.userservice.domain.phish.repository.PhishingRepository;
import com.phishing.userservice.domain.user.domain.User;
import com.phishing.userservice.domain.user.domain.UserRole;
import com.phishing.userservice.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PhishingService {
    private final PhishingRepository phishingRepository;
    private final UserRepository userRepository;

    public void addPhishingData(Long userId, PhishingType phishingType, String value,String content) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Phishing phishing = Phishing.create(creator, phishingType, value,content);
        phishingRepository.save(phishing);
    }

    public List<PhishingResponse> getPhishingDataByType(PhishingType type) {
        return phishingRepository.findByPhishingType(type).stream()
                .map(phishing -> new PhishingResponse(
                        phishing.getPhishingId(),
                        phishing.getPhishingType(),
                        phishing.getValue()
                ))
                .collect(Collectors.toList());
    }

    public List<SearchPhishingResponse> searchPhishingData(PhishingType phishingType, String value) {
        // 정확히 일치하는 피싱 데이터를 리스트로 가져옴
        List<Phishing> phishingDataList = phishingRepository.findByPhishingTypeAndValue(phishingType, value);

        if (phishingDataList.isEmpty()) {
            throw new EntityNotFoundException("해당 타입과 값에 대한 데이터를 찾을 수 없습니다.");
        }

        // 각각의 데이터를 SearchPhishingResponse로 변환하여 리스트로 반환
        return phishingDataList.stream()
                .map(phishingData -> new SearchPhishingResponse(
                        phishingData.getPhishingId(),
                        phishingData.getPhishingType(),
                        phishingData.getValue(),
                        phishingData.getContent(),
                        phishingData.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public List<PhishingResponse> searchPhishingDataByTypeAndValue(PhishingType phishingType, String value) {
        List<Phishing> phishingDataList = phishingRepository.findByPhishingTypeAndValueContaining(phishingType, value);
        return phishingDataList.stream()
                .map(phishingData -> new PhishingResponse(
                        phishingData.getPhishingId(),
                        phishingData.getPhishingType(),
                        phishingData.getValue()
                ))
                .collect(Collectors.toList());
    }


    public void deletePhishingData(Long userId, Long phishingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (user.getUserRole() != UserRole.ADMIN) {
            throw new IllegalStateException("Only admins can delete phishing data");
        }

        Phishing phishing = phishingRepository.findById(phishingId)
                .orElseThrow(() -> new NoSuchElementException("Phishing data not found"));

        phishingRepository.delete(phishing);
    }
}
