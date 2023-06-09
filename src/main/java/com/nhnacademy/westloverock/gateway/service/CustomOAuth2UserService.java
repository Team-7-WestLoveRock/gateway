package com.nhnacademy.westloverock.gateway.service;

import com.nhnacademy.westloverock.gateway.domain.*;
import com.nhnacademy.westloverock.gateway.exception.EmailNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AccountService accountService;
    ParameterizedTypeReference<List<GitEmailDTO>> EMAILS_TYPE = new ParameterizedTypeReference<>() {};


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        List<GitEmailDTO> response = fetchGitUserEmails(userRequest.getAccessToken());
        String primaryEmail = getPrimaryEmailIn(response);

        // primary email을 보고 이미 존재하는 회원인지 확인
        // 아이디가 없으면 회원가입 시킴
        AccountUserIdOnly userId = accountService.fetchByEmail(primaryEmail);
        if (Objects.isNull(userId.getUserId())) {
            String gitHubId = oAuth2User.getAttribute("login");
            String registrationId = userRequest.getClientRegistration().getRegistrationId(); // ex) github, google
            String oAuthBasedPassword = registrationId + "_" + UUID.randomUUID();
            String gitHubName = oAuth2User.getAttribute("name");

            SignupRegisterRequest req = SignupRegisterRequest.builder()
                    .userId(gitHubId)
                    .password(oAuthBasedPassword)
                    .name(gitHubName)
                    .email(primaryEmail)
                    .build();

            oAuth2User.getAttributes().put("password", oAuthBasedPassword);
            oAuth2User.getAttributes().put("email", primaryEmail);
            accountService.registerAccount(req);
        }

        return new CommonUser(userId.getUserId(), oAuth2User.getAttributes());
    }

    private List<GitEmailDTO> fetchGitUserEmails(OAuth2AccessToken accessToken) {
        // NOTE: 일반적으로 OAuth를 통해 받은 값에서 email이 나타나지 않아 따로 이메일을 요청함
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(accessToken.getTokenValue());

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                        "https://api.github.com/user/emails",
                        HttpMethod.GET,
                        requestEntity,
                        EMAILS_TYPE)
                .getBody();
    }

    public String getPrimaryEmailIn(List<GitEmailDTO> emailDTOs) {
        return emailDTOs.stream()
                .filter(g -> g.getPrimary())
                .findFirst()
                .orElseThrow(EmailNotFoundException::new)
                .getEmail();
    }
}
