package com.phishing.userservice.domain.group.service;

import com.phishing.userservice.domain.group.domain.Group;
import com.phishing.userservice.domain.group.domain.GroupMember;
import com.phishing.userservice.domain.group.domain.Invitation;
import com.phishing.userservice.domain.group.payload.request.CreateGroupRequest;
import com.phishing.userservice.domain.group.payload.request.InviteMemberRequest;
import com.phishing.userservice.domain.group.payload.response.InvitationResponse;
import com.phishing.userservice.domain.group.payload.response.MemberInfoResponse;
import com.phishing.userservice.domain.group.repository.GroupMemberRepository;
import com.phishing.userservice.domain.group.repository.GroupRepository;
import com.phishing.userservice.domain.group.repository.InvitationRepository;
import com.phishing.userservice.domain.user.domain.User;
import com.phishing.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;

    public void createGroup(CreateGroupRequest request, Long userId) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        groupRepository.save(
                Group.create(
                        request.name(),
                        creator
                )
        );
    }



    public void inviteMember(Long groupId, Long senderId, InviteMemberRequest request) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new NoSuchElementException("Sender not found"));

        User receiver = userRepository.findByUserInfo_PhnumAndIsDeletedIsFalse(request.receiverPhoneNumber())
                .orElseThrow(() -> new NoSuchElementException("Receiver not found"));


        if (groupMemberRepository.existsByGroup_GroupIdAndUser_UserId(groupId, receiver.getUserId())) {
            throw new IllegalArgumentException("User is already a member of the group.");
        }


        boolean invitationExists = invitationRepository.existsByGroup_GroupIdAndReceiver_UserIdAndStatus(
                groupId, receiver.getUserId(), "PENDING"
        );
        if (invitationExists) {
            throw new IllegalArgumentException("An invitation to this user is already pending.");
        }


        Invitation invitation = Invitation.create(group, sender, receiver);
        invitationRepository.save(invitation);
    }

    public void acceptInvitationAndAddToGroup(Long invitationId, String status) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NoSuchElementException("Invitation not found"));

        invitation.setStatus(status);
        invitationRepository.save(invitation);

        if ("ACCEPTED".equals(status)) {
            addMemberToGroup(invitation.getGroup().getGroupId(), invitation.getReceiver().getUserId());
        }
    }

    private void addMemberToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        boolean isAlreadyMember = groupMemberRepository.existsByGroup_GroupIdAndUser_UserId(groupId, userId);
        if (isAlreadyMember) {
            throw new IllegalArgumentException("User is already a member of this group.");
        }

        GroupMember groupMember = GroupMember.builder()
                .group(group)
                .user(user)
                .isAdmin(false)
                .nickname(user.getUserInfo().getNickname())
                .build();

        groupMemberRepository.save(groupMember);
    }



    public List<MemberInfoResponse> getGroupMembersByGroupId(Long groupId) {
        List<GroupMember> groupMembers = groupMemberRepository.findByGroup_GroupId(groupId);

        if (groupMembers.isEmpty()) {
            throw new NoSuchElementException("No members found in the specified group.");
        }

        return groupMembers.stream()
                .map(groupMember -> new MemberInfoResponse(
                        groupMember.getUser().getUserId(),
                        groupMember.getUser().getUserInfo().getNickname(),
                        groupMember.getUser().getUserInfo().getPhnum()
                ))
                .collect(Collectors.toList());
    }


    public List<InvitationResponse> getReceivedInvitations(Long receiveId) {
        List<Invitation> invitations = invitationRepository.findByReceiver_UserId(receiveId);

        // PENDING 상태인 초대장만 필터링
        return invitations.stream()
                .filter(invitation -> "PENDING".equals(invitation.getStatus()))
                .map(invitation -> new InvitationResponse(
                        invitation.getInvitationId(),
                        invitation.getGroup().getName(),
                        invitation.getSender().getUserInfo().getNickname()
                ))
                .collect(Collectors.toList());
    }

    public void deleteRejectedInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NoSuchElementException("Invitation not found"));

        if (("REJECTED".equals(invitation.getStatus())) &&("ACCEPTED".equals(invitation.getStatus()))) {
            invitationRepository.delete(invitation);
        } else {
            throw new IllegalArgumentException("Only rejected invitations can be deleted");
        }
    }

    public boolean isGroupAdmin(Long groupId, Long userId) {
        GroupMember groupMember = groupMemberRepository.findByGroup_GroupIdAndUser_UserId(groupId, userId)
                .orElseThrow(() -> new NoSuchElementException("Group member not found"));

        return groupMember.isAdmin();
    }

    public void removeGroupMember(Long groupId, Long userId, Long memberIdToRemove) {
        // 1. 그룹장이 맞는지 확인 (creator인지 체크)
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"));

        if (!group.getCreator().getUserId().equals(userId)) {
            throw new SecurityException("Only the group creator can remove members.");
        }

        // 2. 그룹원 존재 여부 확인 (group_members 테이블에서 user_id로 조회)
        GroupMember groupMemberToRemove = groupMemberRepository
                .findByGroup_GroupIdAndUser_UserId(groupId, memberIdToRemove)
                .orElseThrow(() -> new NoSuchElementException("Group member not found"));

        // 3. 그룹원 삭제
        groupMemberRepository.delete(groupMemberToRemove);
    }

    public void editGroupMemberNickname(Long groupId, Long adminId, Long memberId, String newNickname) {
        // 그룹장 확인
        if (!isGroupAdmin(groupId, adminId)) {
            throw new SecurityException("Only group admins can edit member nicknames.");
        }

        // 수정할 멤버 확인
        GroupMember groupMember = groupMemberRepository.findByGroup_GroupIdAndUser_UserId(groupId, memberId)
                .orElseThrow(() -> new NoSuchElementException("Group member not found"));

        // 닉네임 수정
        groupMember.setNickname(newNickname);
        groupMemberRepository.save(groupMember);
    }

    public List<Long> getGroupIdsByCreatorId(Long creatorId) {
        List<Group> groups = groupRepository.findByCreator_UserId(creatorId);
        return groups.stream().map(Group::getGroupId).collect(Collectors.toList());
    }





}
