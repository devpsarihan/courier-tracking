package com.couriertracking.config.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    COURIER_NOT_FOUND_ERROR("001", "Courier is not found.", 404);

    private final String code;
    private final String message;
    private final Integer httpStatusCode;
}
