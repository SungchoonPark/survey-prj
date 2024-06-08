package com.survey.server.image.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record Images(
        List<ImageDto> imageDtos
) {
}
