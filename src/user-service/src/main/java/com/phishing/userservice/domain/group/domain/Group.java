package com.phishing.userservice.domain.group.domain;

import com.phishing.userservice.domain.user.domain.User;
import com.phishing.userservice.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_groups")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<GroupMember> members = new ArrayList<>();

    // 그룹에 멤버 추가하는 메서드
    public void addMember(GroupMember member) {
        members.add(member);
        member.setGroup(this);
    }

    public static Group create(String name, User creator) {
        return Group.builder()
                .name(name)
                .creator(creator)
                .build();
    }


}
