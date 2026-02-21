package com.eunyeong.care_system.config.security;

import com.eunyeong.care_system.component.AccessChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AccessChecker accessCheck;
    // 비밀번호를 BCrypt 방식으로 암호화하는 빈 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 현재는 비활성화 상태
                .authorizeHttpRequests(auth -> auth
                        // 1. CSS, JS, 이미지 파일이 로그인 페이지로 리다이렉트되지 않게 최우선 허용
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()

                        // 2. 이메일 컨트롤러 경로 허용 (/api/mail/mailTest)
                        // @RequestMapping("/api/mail") 이므로 /api/mail/** 로 설정하면 됩니다.
                        .requestMatchers("/api/mail/**").permitAll()
                        // 회원가입
                        .requestMatchers("/api/member/**").permitAll()
                        // 3. 메인, 로그인, 회원가입 관련 경로 허용
                        .requestMatchers("/", "/login", "/signup", "/loginProc", "/about").permitAll()
                        // SpEL 대신 람다식으로 직접 호출
                        .requestMatchers("/visit/**").access((authentication, context) ->
                                new AuthorizationDecision(accessCheck.hasSenior(authentication.get())))

                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/staff/**").hasAnyRole("STAFF", "ADMIN")

                        // 그 외 모든 요청은 로그인 필요
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .exceptionHandling(handler -> handler
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    // 접근이 거부된 URL이 /visit로 시작하는 경우
                    if (request.getRequestURI().startsWith("/visit")) {
                        response.setContentType("text/html; charset=UTF-8");
                        var out = response.getWriter();
                        out.write("<script>" +
                                "alert('등록된 어르신이 없습니다. 마이페이지에서 어르신 등록을 먼저 완료해주세요.');" +
                                "location.href='/mypage';" +
                                "</script>");
                        out.flush();
                    } else {
                        // 그 외의 403 에러는 기본 응답
                        response.sendError(403, "Access Denied");
                    }
                })
        );




        return http.build();
    }
  @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}