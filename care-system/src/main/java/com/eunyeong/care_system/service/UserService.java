package com.eunyeong.care_system.service;

import com.eunyeong.care_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.eunyeong.care_system.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // SecurityConfig에 Bean 등록 필요

    @Transactional
    public void saveMember(User user) {

        String encodedPassword = passwordEncoder.encode(user.getUserPw());


        String userNo = generateUserNo();

        user.setUserNo(userNo);
        user.setUserPw(encodedPassword); // 암호화된 비밀번호로 교체
        user.setUserRole("USER");         // 기본 권한 설정
        user.setAccountStatus("ACTIVE"); // 계정 상태 활성화
        user.setFailCount(0);            // 실패 횟수 초기화
        // 4. DB 삽입
        userRepository.save(user);
    }
    private String generateUserNo() {
        // 11자리 고유 번호 생성 로직 (예: 현재시간 일부 활용)
        return String.valueOf(System.currentTimeMillis()).substring(2, 13);
    }

    public User findByUserId(String username) {
        return userRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 사용자를 찾을 수 없습니다: " + username));
    }

    public boolean isDuplicated(String userId) {
        return userRepository.existsByUserId(userId);
    }

    public boolean isEmailDuplicated(String email) {
        return userRepository.existsByUserEmail(email);
    }
}

