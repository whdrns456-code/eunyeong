package com.eunyeong.care_system.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SENIOR_RELATIONS")
@IdClass(SeniorRelationsId.class) // 위에서 만든 복합키 클래스 연결
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeniorRelations {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENIOR_NO")
    private Seniors senior;

    @Column(name = "RELATION_TYPE", length = 50)
    private String relationType; // "자녀", "부", "모" 등


    @Column(name = "CONTACT_PHONE", length = 15)
    private String contactPhone;
}