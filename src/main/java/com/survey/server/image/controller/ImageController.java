package com.survey.server.image.controller;

import com.survey.server.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
}
