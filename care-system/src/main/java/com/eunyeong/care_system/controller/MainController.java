package com.eunyeong.care_system.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 1. 메인 페이지
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 2. 요양원 소개
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // 3. 일정 안내
    @GetMapping("/schedule")
    public String schedule() {
        return "schedule";
    }

    // 4. 공지사항
    @GetMapping("/notice")
    public String notice() {
        return "notice";
    }

    //5 . 수가 계산
    @GetMapping("/fees")
    public String fees() {
        return "fees";
    }

    // 6. 외출신청
    @GetMapping("/outing")
    public String outing() {
        return "outing";
    }

    // 6. 면회신청
    @GetMapping("/visit")
    public String visit() {
        return "visit";
    }

    // 8. 로그인
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 9. 회원가입
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}