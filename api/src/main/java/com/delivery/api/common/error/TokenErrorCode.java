package com.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
* Token의 경우 2000번대 에러코드 사용
* */
@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs{
    INVALID_TOKEN(400, 2000, "Invalid Token"),
    EXPIRED_TOKEN(400, 2001, "Token has expired"),
    TOKEN_EXCEPTION(400, 2002, "Unknown Token Error"),
    ;

    // 변형이 일어나면 안되니 모두 final로 선언해 변경되지 않는 값으로 초기화.
    private final Integer httpStatusCode;   // 아래와 상응하는 HttpStatusCode

    private final Integer errorCode;    // 내가 만든 ErrorCode

    private final String description;

}
