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
    private String sessionID;
    private String userId;
    private LocalDateTime createdAt;
    private String ipAddress;

    public LoginSession(String sessionID, String userId, String ipAddress) {
        this.sessionID = sessionID;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.ipAddress = ipAddress;
    }
}
