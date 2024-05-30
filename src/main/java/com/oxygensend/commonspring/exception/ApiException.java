package com.oxygensend.commonspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class ApiException  extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
    public HttpStatus getStatusCode() {
        ResponseStatus responseStatus = this.getClass().getAnnotation(ResponseStatus.class);
        return responseStatus.value();
    }

}