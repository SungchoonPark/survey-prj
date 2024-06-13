package com.survey.server.member.domain;

import com.survey.server.survey.domain.Survey;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String pwd;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean surveyComplete;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Survey> surveyList = new ArrayList<>();

    public void surveyComplete() {
        this.surveyComplete = Boolean.TRUE;
    }
}
