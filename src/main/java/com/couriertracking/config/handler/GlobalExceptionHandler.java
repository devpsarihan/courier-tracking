package com.couriertracking.config.handler;

import com.couriertracking.config.handler.exception.CourierTrackingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = CourierTrackingException.class)
    public ResponseEntity<CourierTrackingException> courierTrackingExceptionHandler(CourierTrackingException e) {
        logger.info("Courier Tracking related exception => {}", e.getMessage());
        return new ResponseEntity<>(e, HttpStatus.valueOf(e.getHttpStatusCode()));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<RuntimeException> runtimeExceptionHandler(RuntimeException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
