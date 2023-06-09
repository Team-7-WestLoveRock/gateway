package com.nhnacademy.westloverock.gateway.auth;

import com.nhnacademy.westloverock.gateway.domain.CommonUser;
import com.nhnacademy.westloverock.gateway.domain.LoginSession;
import com.nhnacademy.westloverock.gateway.exception.DormancyUserException;
import com.nhnacademy.westloverock.gateway.exception.WithdrawalUserException;
import com.nhnacademy.westloverock.gateway.repository.LoginSessionRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final LoginSessionRedisRepository loginSessionRedisRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();

        CommonUser commonUser = (CommonUser) authentication.getPrincipal();
        session.setAttribute("username", commonUser.getUsername());
        session.setAttribute("ipAddress", request.getRemoteAddr());

        String userStatus = commonUser.getAttributes().get("status").toString();

        LoginSession loginSession = LoginSession.builder()
                .sessionID(session.getId())
                .userId(commonUser.getUsername())
                .ipAddress(request.getRemoteAddr())
                .build();

        loginSessionRedisRepository.save(loginSession);

        if (userStatus.equals("DORMANCY")) {
            request.setAttribute("message", "휴면 상태 확인");
            request.setAttribute("statusCode", 403);
            request.setAttribute("exception", new DormancyUserException("휴면 상태입니다."));
            request.getRequestDispatcher("error").forward(request, response);
            return;
        }

        response.sendRedirect("/minidooray");
    }
}