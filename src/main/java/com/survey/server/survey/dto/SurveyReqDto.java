package com.survey.server.survey.dto;

import com.survey.server.survey.domain.enums.Rating;

public record SurveyReqDto(
        Long imageId,
        Rating rating
) {
}
