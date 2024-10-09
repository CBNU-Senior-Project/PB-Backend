package com.phishing.userservice.domain.phish.payload.request;

import com.phishing.userservice.domain.phish.domain.PhishingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhishingRequest {
    private PhishingType phishingType;
    private String value;
    private String content;
}
