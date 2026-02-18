package com.eunyeong.care_system.controller;

import com.eunyeong.care_system.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/mailTest")
    public ResponseEntity<String> mailSend(@RequestParam("email") String email) {
        try {
            String authCode = emailService.sendEmail(email);
            return ResponseEntity.ok(authCode); // 상태 코드 200과 함께 번호 전송
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("메일 발송 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}