package com.phishing.userservice.domain.phish.service;

import com.phishing.userservice.domain.phish.domain.Phishing;
import com.phishing.userservice.domain.phish.domain.PhishingType;
import com.phishing.userservice.domain.phish.payload.response.PhishingResponse;
import com.phishing.userservice.domain.phish.repository.PhishingRepository;
import com.phishing.userservice.domain.user.domain.User;
import com.phishing.userservice.domain.user.domain.UserRole;
import com.phishing.userservice.domain.user.repository.UserRepository;
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

    public void addPhishingData(Long userId, PhishingType phishingType, String value) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Phishing phishing = Phishing.create(creator, phishingType, value);
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

    public List<PhishingResponse> searchPhishingData(PhishingType phishingType, String value) {
        List<Phishing> phishingData = phishingRepository.findByPhishingTypeAndValue(phishingType, value);
        return phishingData.stream()
                .map(phishing -> new PhishingResponse(
                        phishing.getPhishingId(),
                        phishing.getPhishingType(),
                        phishing.getValue()
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
