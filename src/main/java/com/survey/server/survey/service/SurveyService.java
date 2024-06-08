package com.survey.server.survey.service;

import com.survey.server.common.enums.CustomResponseStatus;
import com.survey.server.common.exception.CustomException;
import com.survey.server.image.domain.Image;
import com.survey.server.image.repository.ImageRepository;
import com.survey.server.member.domain.Member;
import com.survey.server.member.repository.MemberRepository;
import com.survey.server.survey.domain.Survey;
import com.survey.server.survey.dto.SurveyReqDto;
import com.survey.server.survey.dto.SurveyResultDto;
import com.survey.server.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;

    @Async
    @Transactional
    public void submitSurvey(String username, List<SurveyReqDto> surveyReqDtoList) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND));

        for (SurveyReqDto surveyReqDto : surveyReqDtoList) {
            log.info("imageId : {}, rating : {}", surveyReqDto.imageId(), surveyReqDto.rating());
            Image image = imageRepository.findById(surveyReqDto.imageId()).orElseThrow(() ->
                    new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND
                    ));

            Survey survey = Survey.of(member, image, surveyReqDto.rating());
            surveyRepository.save(survey);
        }
        member.surveyComplete();
    }

    @Transactional(readOnly = true)
    public List<SurveyResultDto> getSurveyResult(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND));

        List<Survey> allSurvey = surveyRepository.findAllByMember(member);
        List<SurveyResultDto> resultDtos = new ArrayList<>();
        log.info("survey size : {}", allSurvey.size());

        for (Survey survey : allSurvey) {
            resultDtos.add(
                    SurveyResultDto.builder()
                            .memberName(member.getUsername())
                            .imageName(survey.getImage().getImageName())
                            .rating(survey.getRating())
                            .build()
            );
        }

        return resultDtos;
    }
}
