package com.eunyeong.care_system.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SENIORS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seniors {

    @Id
    @Column(name = "SENIOR_NO", length = 11)
    private String seniorNo;

    @Column(name = "SENIOR_NAME", nullable = false, length = 50)
    private String seniorName;

    @Column(name = "BIRTH_DATE", nullable = false, length = 8)
    private String birthDate;

    @Column(name = "FLOOR_INFO", length = 5)
    private String floorInfo;


    // 등록한 사람 (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGISTER_ID", nullable = false)
    private User registerUser;
}