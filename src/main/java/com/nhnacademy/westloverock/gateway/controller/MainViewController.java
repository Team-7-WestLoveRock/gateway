package com.nhnacademy.westloverock.gateway.controller;

import com.nhnacademy.westloverock.gateway.domain.SignupRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainViewController {
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "signupForm";
    }

    @PostMapping("/signup")
    public String signup(@Validated SignupRequest req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signupForm";
        }
        System.out.println(req);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
