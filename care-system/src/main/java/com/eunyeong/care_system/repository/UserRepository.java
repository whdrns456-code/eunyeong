package com.eunyeong.care_system.repository;


import com.eunyeong.care_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // 아이디 중복 체크를 위한 메서드

    boolean existsByUserId(String userId);
    Optional<User> findByUserId(String userId);
    boolean existsByUserEmail(String userEmail);
}
