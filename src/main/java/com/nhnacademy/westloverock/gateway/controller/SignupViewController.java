package com.nhnacademy.westloverock.gateway.controller;

import com.nhnacademy.westloverock.gateway.domain.SignupRegisterRequest;
import com.nhnacademy.westloverock.gateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignupViewController {
    private final AccountService accountService;
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupRequest", new SignupRegisterRequest());
        return "signupForm";
    }

    @PostMapping("/signup")
    public String signup(@Validated SignupRegisterRequest req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signupForm";
        }
        accountService.registerAccount(req);
        return "redirect:/home";
    }
}
