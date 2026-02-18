package com.eunyeong.care_system.controller;

import com.eunyeong.care_system.model.SeniorDTO;
import com.eunyeong.care_system.service.SeniorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor // 서비스를 주입받기 위해 꼭 필요함!
public class MainController {

    private final SeniorService seniorService;


    @Value("${api.kakao.key}") // yml에서 가져온 환경변수 값
    private String mapApiKey;

    @GetMapping("/") // 메인 접속 시
    public String index(Model model) {
        // index.html로 지도 키 전달
        model.addAttribute("kakaoKey", mapApiKey);
        return "index"; // index.html 반환
    }

    @GetMapping("/about")
    public String about() { return "about"; }

    @GetMapping("/schedule")
    public String schedule() { return "schedule"; }

    @GetMapping("/notice")
    public String notice() { return "notice"; }

    @GetMapping("/fees")
    public String fees() { return "fees"; }

    @GetMapping("/outing")
    public String outing() { return "outing"; }

    @GetMapping("/visit")
    public String visit() { return "visit"; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/signup")
    public String signup() { return "signup"; }

    // 10. 마이페이지 (새로 추가)
    @GetMapping("/mypage")
    public String myPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            // 현재 로그인한 사용자의 ID(userNo가 ID로 쓰인다면 그 값)로 데이터 조회
            SeniorDTO myData = seniorService.getMyPageData(userDetails.getUsername());
            model.addAttribute("seniorInfo", myData);
        }
        return "mypage";
    }
}