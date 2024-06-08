package com.survey.server.image.service;

import com.survey.server.common.enums.CustomResponseStatus;
import com.survey.server.common.exception.CustomException;
import com.survey.server.image.domain.Image;
import com.survey.server.image.dto.ImageDto;
import com.survey.server.image.dto.Images;
import com.survey.server.image.repository.ImageRepository;
import com.survey.server.member.domain.Member;
import com.survey.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Images getImages(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND));
        if (Boolean.TRUE.equals(member.getSurveyComplete())) {
            throw new CustomException(CustomResponseStatus.SURVEY_COMPLETE);
        }

        log.info("username : {}", username);
        List<Image> images = imageRepository.findAllByUsername(username);
        List<ImageDto> imageTmps = new ArrayList<>();

        for (Image image : images) {
            ImageDto tmpImage = ImageDto.builder()
                    .imageId(image.getId())
                    .imageName(image.getImageName())
                    .imageUrl(image.getImageUrl())
                    .build();

            imageTmps.add(tmpImage);
        }

        return Images.builder()
                .imageDtos(imageTmps)
                .build();
    }

}
