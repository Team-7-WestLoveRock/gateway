package com.nhnacademy.westloverock.gateway.advice;

import com.nhnacademy.westloverock.gateway.exception.DuplicateArgsException;
import com.nhnacademy.westloverock.gateway.exception.UserIllegalStateException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ViewControllerAdvice {
    @ExceptionHandler(DuplicateArgsException.class)
    public String duplicationExceptionHandler(HttpServletRequest request, DuplicateArgsException e) {
        request.setAttribute("message", e.getMessage());
        request.setAttribute("statusCode", e.getStatusCode());
        return "error";
    }

    @ExceptionHandler(UserIllegalStateException.class)
    public String illegalStateExceptionHandler(HttpServletRequest request, UserIllegalStateException e) {
        request.setAttribute("message", e.getMessage());
        request.setAttribute("statusCode", 403);
        request.setAttribute("exception", e);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String duplicationExceptionHandler(HttpServletRequest request, Exception e) {
        request.setAttribute("message", e.getMessage());
        request.setAttribute("statusCode", 500);
        return "error";
    }
}
