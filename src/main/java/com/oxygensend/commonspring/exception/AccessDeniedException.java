package com.oxygensend.commonspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends ApiException {
    public AccessDeniedException() {
        super("Operation not allowed for this user.");
    }
}
