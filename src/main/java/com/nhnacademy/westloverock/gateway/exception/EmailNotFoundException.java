package com.nhnacademy.westloverock.gateway.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException() {
        super("There is no email from user");
    }
}
