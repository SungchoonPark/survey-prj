package com.survey.server.survey.domain;

import com.survey.server.image.domain.Image;
import com.survey.server.member.domain.Member;
import com.survey.server.survey.domain.enums.Rating;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Survey {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    private Survey(Member member, Image image, Rating rating) {
        this.member = member;
        this.image = image;
        this.rating = rating;
    }

    public static Survey of(Member member, Image image, Rating rating) {
        return new Survey(member, image, rating);
    }
}
