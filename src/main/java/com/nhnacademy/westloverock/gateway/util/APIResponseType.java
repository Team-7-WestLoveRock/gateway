package com.nhnacademy.westloverock.gateway.util;

import com.nhnacademy.westloverock.gateway.domain.GitEmailDTO;
import org.springframework.core.ParameterizedTypeReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class APIResponseType {
    public static final ParameterizedTypeReference<Map<String, LocalDate>> CREATE_TYPE = new ParameterizedTypeReference<>() {};
    public static final ParameterizedTypeReference<List<GitEmailDTO>> EMAILS_TYPE = new ParameterizedTypeReference<>() {};
    public static final ParameterizedTypeReference<Map<String, String>> ACCOUNT_STATE_TYPE = new ParameterizedTypeReference<>() {};
    public static final ParameterizedTypeReference<Map<String, LocalDateTime>> LOGIN_LOG_TYPE = new ParameterizedTypeReference<>() {};
}