package com.survey.server.image.dto;

import lombok.Builder;

@Builder
public record ImageDto(
        Long imageId,
        String imageName,
        String imageUrl,
        String category
) {
}
