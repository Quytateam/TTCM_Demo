package com.example.demo.model.response;

public class SomeRuntimeException extends RuntimeException{
    public SomeRuntimeException(String message) {
        super(message);
    }

    public SomeRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
