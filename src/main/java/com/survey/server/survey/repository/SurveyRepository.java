package com.survey.server.survey.repository;

import com.survey.server.member.domain.Member;
import com.survey.server.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> findAllByMember(Member member);
}
