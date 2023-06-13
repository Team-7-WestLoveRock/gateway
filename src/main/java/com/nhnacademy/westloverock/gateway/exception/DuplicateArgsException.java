package com.nhnacademy.westloverock.gateway.exception;

import lombok.Getter;

@Getter
public class DuplicateArgsException extends RuntimeException {
    private int statusCode;
    public DuplicateArgsException(String message) {
        super(message);
        this.statusCode = 400;
    }
}
