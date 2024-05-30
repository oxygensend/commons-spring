package com.oxygensend.commonspring.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ExceptionResponse(HttpStatus.BAD_REQUEST,  error, ex));
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleCustomException(ApiException ex) {
        logger.info("Throwing an exception: " + ex);
        return buildResponseEntity(new ExceptionResponse(ex.getStatusCode(), ex.getMessage()));
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ExceptionResponse apiException = new ExceptionResponse(HttpStatus.BAD_REQUEST, "Validation error", ex);
        apiException.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiException.addValidationException(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiException);
    }


    private ResponseEntity<Object> buildResponseEntity(ExceptionResponse exceptionResponse) {
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getStatus());
    }

}
