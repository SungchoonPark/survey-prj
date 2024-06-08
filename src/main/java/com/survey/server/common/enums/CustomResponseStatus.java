package com.survey.server.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomResponseStatus {

    /***
     * 1000: 요청 성공
     */
    SUCCESS(HttpStatus.OK.value(), "1000", "요청에 성공하였습니다."),

    /***
     * 2000: UNAUTHORIZED
     */
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED.value(), "2000", "만료된 토큰입니다."),
    BAD_JWT(HttpStatus.UNAUTHORIZED.value(), "2001", "잘못된 토큰입니다."),
    SURVEY_COMPLETE(HttpStatus.UNAUTHORIZED.value(), "2002", "이미 설문에 참여했습니다."),


    /***
     * 3000: ACCESS DENIED
     */
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "3000", "인증되지 않은 사용자입니다."),
    LOGOUT_MEMBER(HttpStatus.FORBIDDEN.value(), "3001", "로그아웃된 사용자입니다."),
    ALREADY_MAP_EXIST(HttpStatus.CONFLICT.value(), "3002", "이미 존재하는 채팅 입니다. 새롭게 시작할 수 없습니다."),
    ALREADY_EVALUATION_MAP_EXIST(HttpStatus.CONFLICT.value(), "3003", "평가리스트가 존재합니다. 새롭게 만들 수 없습니다."),

    /***
     * 4000: NOT_FOUND
     */
    NULL_JWT(HttpStatus.NO_CONTENT.value(), "4000", "토큰이 공백입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "4001", "해당 유저를 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "4002", "리프레시 토큰을 찾을 수 없습니다."),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "4003", "해당 권한을 찾을 수 없습니다."),
    GPT_NOT_ANSWER(HttpStatus.NOT_FOUND.value(), "4004", "GPT가 응답하지 않습니다."),

    /***
     * 5000: NOT_MATCH
     */
    REFRESH_TOKEN_NOT_MATCH(HttpStatus.CONFLICT.value(), "5000", "잘못된 리프레시 토큰입니다."),
    BAD_TOKEN(HttpStatus.BAD_REQUEST.value(), "5001", "잘못된 토큰입니다."),
    MEMBER_NOT_MATCH(HttpStatus.FORBIDDEN.value(), "5002", "사용자가 일치하지 않습니다."),

    /***
     * 6000: Server_Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "6000", "내부 서버 오류입니다.");


    private final int httpStatusCode;
    private final String code;
    private final String message;

    CustomResponseStatus(int httpStatusCode, String code, String message) {
        this.httpStatusCode = httpStatusCode;
        this.code = code;
        this.message = message;
    }
}
