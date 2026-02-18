package com.eunyeong.care_system.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "USER_NO")
    private String userNo; // 고유번호 (PK)

    @Column(name = "USER_ID", nullable = false, unique = true)
    private String userId;

    @Column(name = "USER_PW", nullable = false)
    private String userPw;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "USER_EMAIL", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "USER_PHONE")
    private String userPhone;

    @Column(name = "USER_ROLE")
    private String userRole; // USER, ADMIN, EMP

    @Column(name = "FAIL_COUNT")
    private Integer failCount;

    @Column(name = "ACCOUNT_STATUS")
    private String accountStatus; // ACTIVE, SUSPENDED

    @Column(name = "LOCK_UNTIL")
    private String lockUntil;

    @Column(name = "CREATED_AT")
    private String createdAt;

    // 비밀번호 변경이나 실패 횟수 증가 메서드
    public void incrementFailCount() {
        this.failCount++;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }
}
