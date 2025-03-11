package com.phishing.userservice.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.phishing.common.payload.Passport;
import com.phishing.userservice.domain.user.payload.request.EditProfileRequest;
import com.phishing.userservice.domain.user.payload.request.SignUpRequest;
import com.phishing.userservice.domain.user.payload.response.UserInfoResponse;
import com.phishing.userservice.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/api/v1")
@RequiredArgsConstructor
@Validated
public class UserController {
//    private final MailService mailService;
    private final UserService userService;

    @Tag(name = "이메일 중복체크", description = "이메일 중복체크 api, 실패시 400 에러반환")
    @GetMapping("/users/check")
    public ResponseEntity<Void> checkEmail(@RequestParam @Valid @NotNull @Email String email) {
        userService.checkEmail(email);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/email/send")
//    public ResponseEntity<Void> sendMail(@RequestParam @Valid @NotNull String email) {
//        mailService.sendMail(email);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/email/verify")
//    public ResponseEntity<Void> verifyMail(@RequestBody @Valid VerifyEmailRequest request) {
//        mailService.verifyMail(request.email(), request.authCode());
//        return ResponseEntity.ok().build();
//    }

    @Tag(name = "회원가입", description = "회원가입 api")
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok().build();
    }
    @Tag(name = "회원 정보 수정", description = "회원 정보 수정 api, AccessToken 필요")
    @PutMapping("/users/edit")
    public ResponseEntity<Void> editProfile(@RequestHeader("X-Authorization") String passport
            , @RequestBody @Valid EditProfileRequest editProfileRequest) throws JsonProcessingException {
        userService.editProfile(passport, editProfileRequest);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "회원 정보 조회", description = "회원 정보 조회 api, AccessToken 필요")
    @GetMapping("/users/profile")
    public ResponseEntity<UserInfoResponse> viewUserProfile(@RequestHeader("X-Authorization") String passport) throws JsonProcessingException {
        return ResponseEntity.ok(userService.viewUserProfile(passport));
    }

    @Tag(name = "회원 탈퇴", description = "회원 탈퇴 api, AccessToken 필요")
    @PostMapping("/resign")
    public ResponseEntity<Void> resignUser(
            @RequestHeader("X-Authorization") String passport) throws JsonProcessingException {
        userService.resignUser(passport);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "USER_ID를 통한 회원 정보 조회", description = "USER_ID를 통한 회원 정보 조회")
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserInfoResponse> viewUserProfileToId(@PathVariable @Valid Long userId){
        return ResponseEntity.ok(userService.viewUserName(userId));
    }



}
