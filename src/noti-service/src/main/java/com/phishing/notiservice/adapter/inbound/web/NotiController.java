package com.phishing.notiservice.adapter.inbound.web;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/noti/api/v1")
@RequiredArgsConstructor
@Validated
public class NotiController {

}
