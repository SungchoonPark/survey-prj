package com.survey.server.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = jwtUtil.resolveToken(request.getHeader("Authorization"));
        // 쿠키에서 가져오는 값에 대한 코드를 짜기

        final Cookie[] cookies = request.getCookies();
        String token = null;
        String username = null;

        if (cookies != null) {
            token = Arrays.stream(cookies)
                    .filter(cookie -> "token".equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);

            if (token != null) {
                username = jwtUtil.getUsernameInToken(token);
            }
        }
        log.info("[AuthenticationFilter] 요청 들어옴, token : {}", token);

        if (token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Authentication authentication = jwtUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            log.error("Enter [EXPIRED TOKEN]");
//            request.setAttribute(EXCEPTION, CustomResponseStatus.EXPIRED_JWT.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Enter [INVALID TOKEN]");
//            request.setAttribute(EXCEPTION, CustomResponseStatus.BAD_JWT.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    // true는 필터를 안타도 되는것 false는 필터를 타야하는것
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String[] excludePath = {"/api/login", "/exception"};
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}
