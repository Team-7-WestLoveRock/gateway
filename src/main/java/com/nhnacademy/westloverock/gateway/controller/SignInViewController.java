package com.nhnacademy.westloverock.gateway.controller;

import com.nhnacademy.westloverock.gateway.domain.CommonUser;
import com.nhnacademy.westloverock.gateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SignInViewController {
    private final AccountService accountService;

    @GetMapping("/signin/dormancy")
    public String changeStatusPage() {
        return "dormancy";
    }

    @GetMapping("/dormancy/unlock")
    public String unlockUser(@AuthenticationPrincipal CommonUser commonUser) {
        accountService.changeAccountState(commonUser.getUsername(), "활성");
        return "redirect:/minidooray";
    }
}
