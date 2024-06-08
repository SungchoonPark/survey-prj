package com.survey.server.image.controller;

import com.survey.server.common.dto.ApiResponse;
import com.survey.server.common.enums.CustomResponseStatus;
import com.survey.server.config.service.PrincipalDetails;
import com.survey.server.image.dto.Images;
import com.survey.server.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/api/all/images")
    public ResponseEntity<ApiResponse<Images>> getImages(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Images response = imageService.getImages(principalDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

}
