package com.oxygensend.commonspring.exception;

import java.util.Objects;

public class ValidationExceptionResponse implements SubExceptionResponse {
    private String field;
    private Object rejectedValue;
    private final String object;
    private final String message;

    ValidationExceptionResponse(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public ValidationExceptionResponse(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public String getObject() {
        return object;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ValidationExceptionResponse{" +
                "field='" + field + '\'' +
                ", rejectedValue=" + rejectedValue +
                ", object='" + object + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValidationExceptionResponse that = (ValidationExceptionResponse) o;
        return Objects.equals(field, that.field) && Objects.equals(rejectedValue, that.rejectedValue) && Objects.equals(object, that.object) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, rejectedValue, object, message);
    }
}