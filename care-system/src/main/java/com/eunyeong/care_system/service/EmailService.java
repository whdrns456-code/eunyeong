package com.eunyeong.care_system.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private String authNumber; // 생성된 인증번호 저장

    // 1. 6자리 난수 인증번호 생성
    public void createCode() {
        Random random = new Random();
        authNumber = String.valueOf(random.nextInt(888888) + 111111);
    }

    // 2. 메일 발송 양식 만들기
    public MimeMessage createEmailForm(String email) throws MessagingException {
        createCode();
        String setFrom = "eunyeong_care@gmail.com"; // 보내는 사람 (설정값에 맞게 변경)
        String title = "[은영 요양원] 회원가입 인증번호입니다.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        helper.setFrom(setFrom);
        helper.setTo(email);
        helper.setSubject(title);

        // 메일 내용

        // html 문법 적용한 메일의 내용
        String content = """
                    <div style="margin:100px;">
                        <h1> [은영 요양원] 인증번호 안내 </h1>
                        <br>
                        <div align="center" style="border:1px solid black; font-family:verdana;">
                            <h3 style="color:blue;">회원가입 인증번호입니다.</h3>
                            <div style="font-size:130%">
                                CODE : <strong>""" + authNumber + """
                            </strong>
                            </div>
                        </div>
                        <br/>
                    </div>
                    """;
        helper.setText(content, true);

        return message;
    }

    // 3. 실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException {
        MimeMessage message = createEmailForm(toEmail);
        mailSender.send(message);
        return authNumber; // 인증 확인을 위해 번호 반환
    }
}