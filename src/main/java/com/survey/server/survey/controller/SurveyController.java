package com.survey.server.survey.controller;

import com.survey.server.common.dto.ApiResponse;
import com.survey.server.common.enums.CustomResponseStatus;
import com.survey.server.config.service.PrincipalDetails;
import com.survey.server.survey.dto.SurveyReqDto;
import com.survey.server.survey.dto.SurveyResultDto;
import com.survey.server.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SurveyController {
    private final SurveyService surveyService;

    @PostMapping("/api/survey")
    public ResponseEntity<ApiResponse<String>> submitSurvey(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody List<SurveyReqDto> surveyReqDtoList) {
        log.info("come list size : {}", surveyReqDtoList.size());
        surveyService.submitSurvey(principalDetails.getUsername(), surveyReqDtoList);
        return ResponseEntity.ok().body(ApiResponse.createSuccess("Survey submitted. Thank you!", CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/api/admin/survey/{username}")
    public ResponseEntity<ApiResponse<List<SurveyResultDto>>> submitSurvey(@PathVariable String username) {
        List<SurveyResultDto> resp = surveyService.getSurveyResult(username);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(resp, CustomResponseStatus.SUCCESS));
    }

}
