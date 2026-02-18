package com.eunyeong.care_system.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SeniorRequestDTO {
        private String seniorName;   // 어르신 성함
        private String seniorBirth;  // HTML date 타입 (YYYY-MM-DD)
        private String relationship; // 부, 모, 자녀 등
        private String seniorPhone;
}
