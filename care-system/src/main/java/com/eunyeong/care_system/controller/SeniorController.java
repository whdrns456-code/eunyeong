package com.eunyeong.care_system.controller;


import com.eunyeong.care_system.model.SeniorRequestDTO;
import com.eunyeong.care_system.model.User;
import com.eunyeong.care_system.service.SeniorService;
import com.eunyeong.care_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class SeniorController {

    private final SeniorService seniorService;
    private final UserService userService;

    @PostMapping("/register-senior")
    @ResponseBody
    public ResponseEntity<String> registerSenior(SeniorRequestDTO dto,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // 1. 현재 로그인한 사용자(보호자) 정보 조회
            User user = userService.findByUserId(userDetails.getUsername());

            // 2. 서비스 로직 수행 (조회 후 업데이트)
            seniorService.registerNewSenior(dto, user);

            // 3. 성공 응답 전송
            return ResponseEntity.ok("success");

        } catch (RuntimeException e) {
            // 4. 실패 시 서비스에서 던진 에러 메시지("명단에 없음" 등)를 전송
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다.");
        }
    }
}
