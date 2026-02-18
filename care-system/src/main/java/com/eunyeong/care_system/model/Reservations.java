package com.eunyeong.care_system.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVATIONS", uniqueConstraints = {
        @UniqueConstraint(name = "UK_SENIOR_RESERVATION", columnNames = {"SENIOR_NO", "START_DATE"})
})
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservations {

    @Id
    @Column(name = "RES_NO", length = 15)
    private String resNo;

    // 신청 보호자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO", nullable = false)
    private User user;

    // 대상 어르신
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENIOR_NO", nullable = false)
    private Seniors senior;

    @Column(name = "RES_TYPE", length = 10)
    private String resType; // VISIT, OUTING, STAY

    @Column(name = "START_DATE", nullable = false, length = 15)
    private String startDate;

    @Column(name = "END_DATE", nullable = false, length = 15)
    private String endDate;

    @Column(name = "APPLICANT_TEL", nullable = false, length = 13)
    private String applicantTel;

    @Column(name = "RES_STATUS", length = 15)
    private String resStatus = "PENDING";

    @Column(name = "REMARK", length = 500)
    private String remark;

    @Column(name = "CREATED_AT", length = 15)
    private String createdAt;


    // 어르신 등록 시 입력했던 정보를 예약 시점에 스냅샷처럼 저장
    @Column(name = "RELATION_TYPE", length = 20)
    private String relationType;
    @Column(name = "CONTACT_PHONE", length = 15)
    private String contactPhone;

}