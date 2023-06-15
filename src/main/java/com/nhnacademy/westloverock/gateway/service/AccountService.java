package com.nhnacademy.westloverock.gateway.service;

import com.nhnacademy.westloverock.gateway.config.ApiProperties;
import com.nhnacademy.westloverock.gateway.domain.*;
import com.nhnacademy.westloverock.gateway.exception.EmailNotFoundException;
import com.nhnacademy.westloverock.gateway.util.APIResponseType;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Setter
@Service
@RequiredArgsConstructor
public class AccountService {
    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final ApiProperties apiProperties;
    private final PasswordEncoder passwordEncoder;
    private static final String ACCOUNT_API_ACCOUNTS = "/account/api/accounts/";

    public AccountDTO fetchByUserId(String username) {
        String requestUrl = apiProperties.getAccountUrl() +
                ACCOUNT_API_ACCOUNTS +
                username;

        return restTemplate.exchange(requestUrl,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        AccountDTO.class)
                .getBody();
    }

    public Map<String, LocalDate> registerAccount(SignUpRegisterRequest signupRegisterRequest) {
        signupRegisterRequest.setPassword(passwordEncoder.encode(signupRegisterRequest.getPassword()));
        String requestUrl = apiProperties.getAccountUrl() +
                ACCOUNT_API_ACCOUNTS;

        HttpEntity<SignUpRegisterRequest> httpEntity = new HttpEntity<>(signupRegisterRequest, httpHeaders);

        return restTemplate.exchange(requestUrl,
                        HttpMethod.POST,
                        httpEntity,
                        APIResponseType.CREATE_TYPE)
                .getBody();
    }

    public Optional<AccountUserIdOnly> fetchByEmail(String primaryEmail) {
        String requestUrl = apiProperties.getAccountUrl() +
                ACCOUNT_API_ACCOUNTS + "email/" +
                primaryEmail;

        return Optional.ofNullable(restTemplate.exchange(requestUrl,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        AccountUserIdOnly.class)
                .getBody());
    }

    public List<GitEmailDTO> fetchGitUserEmails(OAuth2AccessToken accessToken) {
        // NOTE: 일반적으로 OAuth를 통해 받은 값에서 email이 나타나지 않아 따로 이메일을 요청함
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(accessToken.getTokenValue());

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                        "https://api.github.com/user/emails",
                        HttpMethod.GET,
                        requestEntity,
                        APIResponseType.EMAILS_TYPE)
                .getBody();
    }

    public boolean fetchUserIsExist(String userId) {
        String requestUrl = apiProperties.getAccountUrl() +
                ACCOUNT_API_ACCOUNTS +
                userId +
                "/exist";

        return Boolean.TRUE.equals(restTemplate.exchange(requestUrl,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        Boolean.class)
                .getBody());
    }

    public void saveUserLoginLog(String userId, LoginLogSaveDTO loginLogSaveDTO) {
        String requestUrl = apiProperties.getAccountUrl() +
                ACCOUNT_API_ACCOUNTS +
                userId +
                "/loginLog";

        HttpEntity<LoginLogSaveDTO> httpEntity = new HttpEntity<>(loginLogSaveDTO, httpHeaders);

        restTemplate.exchange(requestUrl,
                HttpMethod.POST,
                httpEntity,
                APIResponseType.LOGIN_LOG_TYPE);
    }

    public void changeAccountState(String userId, String state) {
        String requestUrl = apiProperties.getAccountUrl() +
                ACCOUNT_API_ACCOUNTS +
                userId;

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(Map.of("state", state), httpHeaders);

        restTemplate.exchange(requestUrl,
                HttpMethod.POST,
                httpEntity,
                APIResponseType.ACCOUNT_STATE_TYPE);
    }

    public String getPrimaryEmailIn(List<GitEmailDTO> emailDTOs) {
        return emailDTOs.stream()
                .filter(GitEmailDTO::getPrimary)
                .findFirst()
                .orElseThrow(EmailNotFoundException::new)
                .getEmail();
    }
}
