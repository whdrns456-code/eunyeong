package com.eunyeong.care_system.repository;

import com.eunyeong.care_system.model.Seniors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeniorRepository extends JpaRepository<Seniors, String> {
    // 1. 서비스에서 부르는 이름과 정확히 일치시켜야 함
    boolean existsByRegisterUser_UserNo(String userNo);
    boolean existsByRegisterUser_UserId(String userId);
    // 2. 마이페이지 데이터 조회용
    List<Seniors> findByRegisterUser_UserNo(String userNo);

    Optional<Seniors> findBySeniorNameAndBirthDate(String seniorName, String birthDate);
}