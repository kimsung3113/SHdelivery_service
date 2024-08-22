package com.delivery.api.common.exception;

import com.delivery.api.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();

    String getErrorDescription();

}
