package com.nhnacademy.westloverock.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter @Setter
@ConfigurationProperties("api.url")
public class ApiProperties {
    private String accountUrl;
    private String taskUrl;
}
