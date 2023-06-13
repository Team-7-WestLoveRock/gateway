package com.nhnacademy.westloverock.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SignInViewController {

    @GetMapping("/signin/dormancy")
    public String changeStatusPage() {
        return "dormancy";
    }
}
