package com.couriertracking.config.handler.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierTrackingException extends RuntimeException{

    private final String code;
    private final String message;
    private final Integer httpStatusCode;

    public CourierTrackingException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.httpStatusCode = errorCode.getHttpStatusCode();
    }

    public CourierTrackingException(String code, String message, Integer httpStatusCode) {
        super(message);
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
