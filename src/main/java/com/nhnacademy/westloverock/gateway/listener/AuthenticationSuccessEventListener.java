package com.nhnacademy.westloverock.gateway.listener;

import com.nhnacademy.westloverock.gateway.domain.CommonUser;
import com.nhnacademy.westloverock.gateway.domain.LoginSession;
import com.nhnacademy.westloverock.gateway.repository.LoginSessionRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private final LoginSessionRedisRepository sessionRedisRepository;

    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
        CommonUser userInfo = (CommonUser) e.getAuthentication().getPrincipal();

        LoginSession loginSession = new LoginSession(userInfo.getUsername(), auth.getRemoteAddress());
        sessionRedisRepository.save(loginSession);
    }
}