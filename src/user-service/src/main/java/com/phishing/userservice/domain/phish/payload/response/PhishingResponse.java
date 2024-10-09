package com.phishing.userservice.domain.phish.payload.response;

import com.phishing.userservice.domain.phish.domain.PhishingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhishingResponse {

    private Long phishingId;
    private PhishingType phishingType;
    private String value;
    private String content;

}
