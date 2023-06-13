package com.nhnacademy.westloverock.gateway.controller;

import com.nhnacademy.westloverock.gateway.domain.SignUpRegisterRequest;
import com.nhnacademy.westloverock.gateway.exception.DuplicateArgsException;
import com.nhnacademy.westloverock.gateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SignUpViewController {
    private final AccountService accountService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signUpRegisterRequest", new SignUpRegisterRequest());
        return "signupForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignUpRegisterRequest req,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("signUpRegisterRequest", req);
            return "signupForm";
        }

        if (accountService.fetchUserIsExist(req.getUserId())) {
            throw new DuplicateArgsException("이미 존재하는 아이디 입니다");
        }

        if (accountService.fetchByEmail(req.getEmail()).isPresent()) {
            throw new DuplicateArgsException("이미 존재하는 이메일입니다");
        }

        accountService.registerAccount(req);
        return "redirect:/minidooray";
    }
}
