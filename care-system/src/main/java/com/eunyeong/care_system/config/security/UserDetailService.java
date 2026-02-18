package com.eunyeong.care_system.config.security;

import com.eunyeong.care_system.model.User;
import com.eunyeong.care_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. DB에서 아이디로 사용자 찾기
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다: " + username));

        // 2. 시큐리티가 이해할 수 있는 UserDetails 객체로 변환하여 반환
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(user.getUserPw()) // 암호화된 비밀번호
                .roles(user.getUserRole())  // 예: "USER", "ADMIN", "STAFF"
                .build();
    }

}
