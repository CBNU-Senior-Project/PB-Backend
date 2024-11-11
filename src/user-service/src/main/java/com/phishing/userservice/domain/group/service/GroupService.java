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
import com.phishing.userservice.domain.user.domain.UserInfo;
import com.phishing.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final GcsService gcsService;

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

        // 자기 자신의 전화번호로 초대하려는 경우 예외 발생
        if (sender.getUserInfo().getPhnum().equals(request.receiverPhoneNumber())) {
            throw new IllegalArgumentException("You cannot invite yourself using your own phone number.");
        }

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
                .imagename("default.jpg")
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
                .map(groupMember -> {
                    String imageUrl = gcsService.downloadImage(groupMember.getImagename()); // 다운로드 링크 생성
                    return new MemberInfoResponse(
                            groupMember.getUser().getUserId(),
                            groupMember.getNickname(),
                            groupMember.getUser().getUserInfo().getPhnum(),
                            imageUrl // 추가된 URL
                    );
                })
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

    public void editGroupMemberNickname(Long groupId, Long adminId, Long userId, String newNickname) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"));

        if (!group.getCreator().getUserId().equals(adminId)) {
            throw new SecurityException("Only the group creator can edit member nicknames.");
        }

        // 수정할 멤버 확인 - groupId와 userId로 GroupMember 찾기
        GroupMember groupMember = groupMemberRepository.findByGroup_GroupIdAndUser_UserId(groupId, userId)
                .orElseThrow(() -> new NoSuchElementException("Group member not found"));

        // 닉네임 수정
        groupMember.setNickname(newNickname);
        groupMemberRepository.save(groupMember);
    }

    public void editGroupMemberImage(Long groupId, Long adminId, Long userId, String newImage) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"));

        if (!group.getCreator().getUserId().equals(adminId)) {
            throw new SecurityException("Only the group creator can edit member nicknames.");
        }

        // 수정할 멤버 확인 - groupId와 userId로 GroupMember 찾기
        GroupMember groupMember = groupMemberRepository.findByGroup_GroupIdAndUser_UserId(groupId, userId)
                .orElseThrow(() -> new NoSuchElementException("Group member not found"));

        // 닉네임 수정
        groupMember.setImagename(newImage);
        groupMemberRepository.save(groupMember);
    }




    public List<Long> getGroupIdsByCreatorId(Long creatorId) {
        List<Group> groups = groupRepository.findByCreator_UserId(creatorId);
        return groups.stream().map(Group::getGroupId).collect(Collectors.toList());
    }


    public List<UserInfo> getGroupLeaderInfo(Long userId) {
        // Find the groups the user is a member of
        List<GroupMember> groupMembers = groupMemberRepository.findByUser_UserId(userId);

        List<UserInfo> groupLeadersInfo = new ArrayList<>();

        // Iterate through the group members to get the group ID
        for (GroupMember groupMember : groupMembers) {
            Long groupId = groupMember.getGroup().getGroupId();  // Get the group ID

            // Fetch the creator (group leader) from the group
            Group group = groupMember.getGroup();
            Long creatorId = group.getCreator().getUserId();  // Get the creator's userId (group leader)

            // Retrieve the group leader's information (nickname, phone number, etc.)
            User groupLeader = userRepository.findById(creatorId).orElseThrow(() -> new NoSuchElementException("Group leader not found"));

            // Add the leader's user info to the list
            groupLeadersInfo.add(groupLeader.getUserInfo());
        }

        // If no group is found for the user, throw an exception
        if (groupLeadersInfo.isEmpty()) {
            throw new NoSuchElementException("User is not part of any group with a leader.");
        }

        // Return the list of group leaders' info
        return groupLeadersInfo;
    }







}