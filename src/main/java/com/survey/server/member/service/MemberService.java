package com.survey.server.member.service;

import com.survey.server.common.enums.CustomResponseStatus;
import com.survey.server.common.exception.CustomException;
import com.survey.server.config.JwtUtil;
import com.survey.server.member.domain.Member;
import com.survey.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public String login(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND));

        return jwtUtil.createToken(member.getUsername());
    }

}
