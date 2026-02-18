package com.eunyeong.care_system.repository;

import com.eunyeong.care_system.model.SeniorRelations;
import com.eunyeong.care_system.model.SeniorRelationsId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SeniorRelationsRepository extends JpaRepository<SeniorRelations, SeniorRelationsId> {
    // 1. [기존] 유저 번호로만 찾기 (어르신이 한 명일 때만 안전함)
    Optional<SeniorRelations> findByUser_UserNo(String userNo);
    // 2. [추가] 유저 번호 + 어르신 번호로 정확하게 '그 관계'만 찾기
    // 이 메서드를 쓰면 어르신이 여러 명이어도 에러가 나지 않습니다.
    Optional<SeniorRelations> findByUser_UserNoAndSenior_SeniorNo(String userNo, String seniorNo);
}