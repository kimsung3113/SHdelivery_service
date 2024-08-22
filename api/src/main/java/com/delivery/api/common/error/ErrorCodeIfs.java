package com.delivery.api.common.error;

public interface ErrorCodeIfs {

    Integer getHttpStatusCode();

    Integer getErrorCode();

    String getDescription();

}
