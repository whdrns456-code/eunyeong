package com.eunyeong.care_system.controller;

import com.eunyeong.care_system.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public String mailSend(@RequestParam("email") String email) throws Exception {
        return emailService.sendEmail(email);
    }
}