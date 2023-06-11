package com.nhnacademy.westloverock.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("minidooray")
public class MainViewController {
    @GetMapping({"", "home"})
    public String home() {
        return "home";
    }
}
