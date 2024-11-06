package com.phishing.userservice.domain.group.domain;

import com.phishing.userservice.domain.user.domain.User;
import com.phishing.userservice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "group_invitations")
public class Invitation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private Long invitationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;  // 그룹장

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;  // 초대받은 유저

    @Column(name = "status", nullable = false)
    private String status; // PENDING, ACCEPTED, REJECTED 등




    public void setStatus(String status) {
        this.status = status;
    }


    public static Invitation create(Group group, User sender, User receiver) {
        return Invitation.builder()
                .group(group)
                .sender(sender)
                .receiver(receiver)
                .status("PENDING")
                .build();
    }
}
