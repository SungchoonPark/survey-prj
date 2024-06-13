package com.survey.server.survey.dto;

import com.survey.server.survey.domain.enums.Rating;
import lombok.Builder;

@Builder
public record SurveyResultDto(
    String memberName,
    String imageName,
    String category,
    Rating rating
) {
}
