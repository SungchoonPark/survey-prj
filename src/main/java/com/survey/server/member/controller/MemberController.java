package com.survey.server.member.controller;

import com.survey.server.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/api/check")
    public ResponseEntity defaultController() {
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody String username, HttpServletResponse response) {
        log.info("로그인 요청 들어왔음 : {}", username);
        String token = memberService.login(username);

        log.info("토큰 : {}", token);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        cookie.setMaxAge(60 * 60 * 10); // 10시간
        response.addCookie(cookie);

        return ResponseEntity.ok("로그인 완료");
    }

}
