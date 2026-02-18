package com.eunyeong.care_system.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SeniorDTO {
    // === 보호자(본인) 정보 ===
    private String userName;
    private String userEmail;
    private String userPhone;
    private String createdAt;   // User 엔티티의 필드명과 맞춤

    // === 매칭된 어르신 정보 ===
    private String seniorName;
    private String relationship;


    // 추가: 연결된 어르신들의 리스트
    private List<SeniorInfo> seniors;
    @Data
    @Builder
    public static class SeniorInfo {
        private String seniorName;
        private String relationship;
    }

}