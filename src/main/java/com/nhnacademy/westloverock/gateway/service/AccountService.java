package com.nhnacademy.westloverock.gateway.service;

import com.nhnacademy.westloverock.gateway.config.ApiProperties;
import com.nhnacademy.westloverock.gateway.domain.AccountDTO;
import com.nhnacademy.westloverock.gateway.domain.AccountUserIdOnly;
import com.nhnacademy.westloverock.gateway.domain.LoginLogSaveDTO;
import com.nhnacademy.westloverock.gateway.domain.SignupRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private static final ParameterizedTypeReference<Map<String, LocalDate>> CREATE_RESPONSE_TYPE = new ParameterizedTypeReference<>() {};

    public Optional<AccountDTO> fetchByUserId(String username) {
        String requestUrl = apiProperties.getAccountUrl() +
                ACCOUNT_API_ACCOUNTS +
                username;

        AccountDTO accountDTO = restTemplate.exchange(requestUrl,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        AccountDTO.class)
                .getBody();

        return Optional.ofNullable(restTemplate.exchange(requestUrl,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        AccountDTO.class)
                .getBody());
    }

    public Map<String, LocalDate> registerAccount(SignupRegisterRequest signupRegisterRequest) {
        signupRegisterRequest.setPassword(passwordEncoder.encode(signupRegisterRequest.getPassword()));
        String requestUrl = apiProperties.getAccountUrl() +
                ACCOUNT_API_ACCOUNTS;

        HttpEntity<SignupRegisterRequest> httpEntity = new HttpEntity<>(signupRegisterRequest, httpHeaders);

        return restTemplate.exchange(requestUrl,
                        HttpMethod.POST,
                        httpEntity,
                        CREATE_RESPONSE_TYPE)
                .getBody();
    }

    public Optional<AccountUserIdOnly> fetchByEmail(String primaryEmail) {
        String requestUrl = apiProperties.getAccountUrl() +
                "/account/api/accounts/email/" +
                primaryEmail;

        return Optional.ofNullable(restTemplate.exchange(requestUrl,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        AccountUserIdOnly.class)
                .getBody());
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
                new ParameterizedTypeReference<Map<String, LocalDateTime>>() {
                });
    }
}
