package com.nhnacademy.westloverock.gateway.service;

import com.nhnacademy.westloverock.gateway.domain.AccountUserIdOnly;
import com.nhnacademy.westloverock.gateway.domain.CommonUser;
import com.nhnacademy.westloverock.gateway.domain.GitEmailDTO;
import com.nhnacademy.westloverock.gateway.domain.SignUpRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AccountService accountService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        HashMap<String, Object> attributes = new LinkedHashMap<>(oAuth2User.getAttributes());

        List<GitEmailDTO> response = accountService.fetchGitUserEmails(userRequest.getAccessToken());
        String primaryEmail = accountService.getPrimaryEmailIn(response);

        // primary email을 보고 이미 존재하는 회원인지 확인
        // NOTE: 아이디가 없으면 회원가입 시킴
        Optional<AccountUserIdOnly> userIdDTO = accountService.fetchByEmail(primaryEmail);
        String userId;
        if (userIdDTO.isEmpty()) {
            String gitHubId = oAuth2User.getAttribute("login");
            String registrationId = userRequest.getClientRegistration().getRegistrationId(); // ex) github, google
            String oAuthBasedPassword = registrationId + "_" + UUID.randomUUID();
            String gitHubName = oAuth2User.getAttribute("name");

            SignUpRegisterRequest req = SignUpRegisterRequest.builder()
                    .userId(gitHubId)
                    .password(oAuthBasedPassword)
                    .name(gitHubName)
                    .email(primaryEmail)
                    .build();

            attributes.put("password", oAuthBasedPassword);
            attributes.put("email", primaryEmail);
            accountService.registerAccount(req);
            userId = gitHubId;
        } else {
            userId = userIdDTO.get().getUserId();
        }
        return new CommonUser(userId, attributes);
    }
}
