package com.nhnacademy.westloverock.gateway.domain;


import lombok.Setter;

@Setter
public class AccountUserIdOnly {
    private String userId;
    public String getUserId() {
        return this.userId;
    }
}
