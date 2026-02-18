package com.eunyeong.care_system.controller;

import com.eunyeong.care_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.eunyeong.care_system.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("checkId")
    public ResponseEntity<Boolean>checkId(@RequestBody Map<String, String> request){
        String userId = request.get("userId");
        boolean isDuplicated = userService.isDuplicated(userId);
        return ResponseEntity.ok(isDuplicated);
    }
    @PostMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(@RequestBody Map<String, String> request) {
        String email = request.get("userEmail");
        // 서비스 레이어의 중복 체크 메서드 호출
        boolean isDuplicated = userService.isEmailDuplicated(email);
        return ResponseEntity.ok(isDuplicated);
    }

    @PostMapping("/register")
    public ResponseEntity<String>register(@RequestBody User user){
        try{
            userService.saveMember(user);
            return ResponseEntity.ok("회원가입 완료되었습니다.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("가입 중 오류 발생: " + e.getMessage());
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
