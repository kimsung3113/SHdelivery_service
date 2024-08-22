package com.delivery.api.exceptionhandler;

import com.delivery.api.common.api.Api;
import com.delivery.api.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE) // 최우선 처리
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(ApiException apiException) {

        // 왜 stacktrace가 가능하냐면 RuntimeException을 상속받았기 때문
        log.error("", apiException);

        var errorCode = apiException.getErrorCodeIfs();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCode, apiException.getErrorDescription()) // 이 방식이 제일 좋다.
                );

    }


}
