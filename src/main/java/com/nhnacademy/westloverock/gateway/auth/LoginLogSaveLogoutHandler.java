package com.nhnacademy.westloverock.gateway.auth;

import com.nhnacademy.westloverock.gateway.domain.LoginLogSaveDTO;
import com.nhnacademy.westloverock.gateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
public class LoginLogSaveLogoutHandler implements LogoutHandler {
    private final AccountService accountService;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        HttpSession session = request.getSession(false);

        String userId = session.getAttribute("username").toString();
        String ipAddress = session.getAttribute("ipAddress").toString();

        LoginLogSaveDTO loginLogSaveDTO = new LoginLogSaveDTO();
        loginLogSaveDTO.setIpAddress(ipAddress);

        accountService.saveUserLoginLog(userId, loginLogSaveDTO);
    }
}
