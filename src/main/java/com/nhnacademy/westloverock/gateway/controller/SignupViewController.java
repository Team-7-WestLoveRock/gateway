package com.nhnacademy.westloverock.gateway.controller;

import com.nhnacademy.westloverock.gateway.domain.SignupRegisterRequest;
import com.nhnacademy.westloverock.gateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SignupViewController {
    private final AccountService accountService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupRegisterRequest", new SignupRegisterRequest());
        return "signupForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupRegisterRequest req,
                         BindingResult bindingResult,
                         Model model) {
        if (accountService.fetchUserIsExist(req.getUserId())) {
            model.addAttribute("duplicationMessage", "이미 존재하는 아이디입니다.");
            model.addAttribute("signupRegisterRequest", req);
            return "signupForm";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("signupRegisterRequest", req);
            return "signupForm";
        }
        accountService.registerAccount(req);
        return "redirect:/minidooray";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();
        return "redirect:/";
    }
}
