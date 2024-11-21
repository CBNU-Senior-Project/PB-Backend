package com.phishing.notiservice.adapter.outbound.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@DynamicUpdate
@Entity
@Table(name = "group_members")
public class GroupMemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private Long groupMemberId;

    @Column(name = "group_id", nullable = false)
    private Long group;

    @Column(name = "user_id", nullable = false)
    private Long user;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    @Column(name = "nickname")  // 새로운 칼럼 추가
    private String nickname;  // 기본값은 null로 설정됨


    @Column(name = "imagename")
    private String imagename; // 필드 초기화

}
