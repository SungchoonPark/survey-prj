package com.survey.server.common.exception;

import com.survey.server.common.enums.CustomResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private final CustomResponseStatus customResponseStatus;
}
