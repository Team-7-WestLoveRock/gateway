package com.nhnacademy.westloverock.gateway.auth;

import com.nhnacademy.westloverock.gateway.domain.CommonUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        CommonUser commonUser = (CommonUser) authentication.getPrincipal();
        session.setAttribute("username", commonUser.getUsername());
        session.setAttribute("ipAddress", request.getRemoteAddr());

        response.sendRedirect("/minidooray");
    }
}