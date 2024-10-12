package com.phishing.userservice.domain.group.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phishing.common.payload.Passport;
import com.phishing.userservice.domain.group.payload.request.CreateGroupRequest;
import com.phishing.userservice.domain.group.payload.request.EditInviteRequest;
import com.phishing.userservice.domain.group.payload.request.EditNicknameRequest;
import com.phishing.userservice.domain.group.payload.request.InviteMemberRequest;
import com.phishing.userservice.domain.group.payload.response.InvitationResponse;
import com.phishing.userservice.domain.group.payload.response.MemberInfoResponse;
import com.phishing.userservice.domain.group.service.GroupService;
import com.phishing.userservice.global.component.token.TokenResolver;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    //private final TokenResolver tokenResolver;
    private final ObjectMapper objectMapper;

    @Tag(name = "그룹 생성", description = "그룹 생성 API, AccessToken 필요")
    @PostMapping
    public ResponseEntity<Void> createGroup(
            @RequestHeader("X-Authorization") String token,
            @RequestBody CreateGroupRequest request) throws JsonProcessingException {

//        Long userId = tokenResolver.getAccessClaims(token);
        Passport passport = objectMapper.readValue(token, Passport.class);
        groupService.createGroup(request, passport.userId());

        return ResponseEntity.ok().build();
    }

    @Tag(name = "그룹원 초대 메시지 전송", description = "그룹장이 그룹원을 초대하는 API, AccessToken 필요")
    @PostMapping("/{groupId}/invite")
    public ResponseEntity<Void> inviteMember(@RequestHeader("X-Authorization") String token, @PathVariable Long groupId, @RequestBody InviteMemberRequest request) throws JsonProcessingException {

        Passport passport = objectMapper.readValue(token, Passport.class);
        //Long senderId = tokenResolver.getAccessClaims(token);
        groupService.inviteMember(groupId,passport.userId(), request);
        return ResponseEntity.ok().build();
    }



    @Tag(name = "초대 수락 ", description = "초대가 ACCEPTED된 후 그룹에 멤버를 추가하는 API")
    @PatchMapping("/invitations/{invitationId}/status")
    public ResponseEntity<Void> updateInvitationStatusAndAddMember(
            @PathVariable Long invitationId,
            @RequestBody EditInviteRequest request) {
        groupService.acceptInvitationAndAddToGroup(invitationId, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @Tag(name = "그룹 멤버 조회", description = "그룹에 속한 멤버들의 userId, 이름, 전화번호 리스트를 조회하는 API")
    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<MemberInfoResponse>> getGroupMembers(@PathVariable Long groupId) {
        List<MemberInfoResponse> memberInfos = groupService.getGroupMemberIds(groupId);
        return ResponseEntity.ok(memberInfos);
    }


    @Tag(name = "초대장 조회", description = "지정된 사용자의 초대장 리스트를 조회하는 API")
    @GetMapping("/invitations/{receive_id}")
    public ResponseEntity<List<InvitationResponse>> getReceivedInvitations(
            @RequestHeader("X-Authorization") String token,
            @PathVariable("receive_id") Long receiveId) {
        try {
            // ObjectMapper를 사용하여 토큰을 Passport 객체로 변환
            Passport passport = objectMapper.readValue(token, Passport.class);
            Long userId = passport.userId();  // Passport 객체에서 userId 추출

            // 사용자 ID가 요청된 receiveId와 일치하지 않으면 접근 거부
            if (!userId.equals(receiveId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            // 초대장 리스트를 가져옴
            List<InvitationResponse> invitations = groupService.getReceivedInvitations(receiveId);
            return ResponseEntity.ok(invitations);

        } catch (Exception e) {
            // 예외 처리: 토큰 파싱 오류나 기타 예외 상황 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @Tag(name = "초대장 삭제", description = "REJECTED,ACCEPTED 상태인 초대장을 삭제하는 API")
    @DeleteMapping("/invitations/{invitationId}")
    public ResponseEntity<Void> deleteRejectedInvitation(
            @PathVariable Long invitationId) {

        try {
            groupService.deleteRejectedInvitation(invitationId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Tag(name = "그룹원 삭제", description = "그룹장이 그룹원을 삭제하는 API")
    @DeleteMapping("/{groupId}/members/{memberId}")
    public ResponseEntity<Void> removeGroupMember(
            @RequestHeader("X-Authorization") String token,
            @PathVariable Long groupId,
            @PathVariable Long memberId) throws JsonProcessingException {

        Passport passport = objectMapper.readValue(token, Passport.class);
         Long userId = passport.userId();
        //Long userId = tokenResolver.getAccessClaims(token);

        try {
            groupService.removeGroupMember(groupId, userId, memberId);
            return ResponseEntity.ok().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Tag(name = "그룹 멤버 닉네임 수정", description = "그룹장이 특정 그룹 멤버의 닉네임을 수정하는 API")
    @PatchMapping("/{groupId}/members/{memberId}/nickname")
    public ResponseEntity<Void> editGroupMemberNickname(
            @RequestHeader("X-Authorization") String token,
            @PathVariable Long groupId,
            @PathVariable Long memberId,
            @RequestBody EditNicknameRequest request) throws JsonProcessingException {

        Passport passport = objectMapper.readValue(token, Passport.class);
        Long  adminId = passport.userId();
        //Long adminId = tokenResolver.getAccessClaims(token);  // 토큰으로 그룹장 ID 가져오기
        groupService.editGroupMemberNickname(groupId, adminId, memberId, request.getNickname());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/creator/{creatorId}/group-ids")
    public ResponseEntity<List<Long>> getGroupIdsByCreator(@PathVariable Long creatorId) {
        List<Long> groupIds = groupService.getGroupIdsByCreatorId(creatorId);
        return ResponseEntity.ok(groupIds);
    }


}
