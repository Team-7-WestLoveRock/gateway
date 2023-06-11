package com.nhnacademy.westloverock.gateway.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@RedisHash(value = "loginSession", timeToLive = 3600)
public class LoginSession implements Serializable {
    @Id
    private String userId;
    private LocalDateTime loginAt;
    private String ipAddress;

    public LoginSession(String userId, String ipAddress) {
        this.userId = userId;
        this.loginAt = LocalDateTime.now();
        this.ipAddress = ipAddress;
    }
}
