package com.survey.server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    // CORS 해결
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000")); // ⭐️ 허용할 origin
            config.setAllowCredentials(true);
            return config;
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 토큰 방식을 위한 STATELESS 선언
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // CORS 해결하기 위한 코드 추가
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                // 권한 규칙 설정 (API 명세에 맞게 수정 필요)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/member/**").hasRole("USER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/all/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().permitAll()
                )
                // 커스텀 JWT 핸들러 및 엔트리 포인트를 사용하기 위해 httpBasic disable
                .httpBasic(AbstractHttpConfigurer::disable)
                // 인증 및 인가에 대한 예외 처리를 다룸
                // JWT Filter 를 필터체인에 끼워넣어줌
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
