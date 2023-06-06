package com.nhnacademy.westloverock.gateway.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class AccountDTO implements Serializable {
    private String userId;
    private String password;
    private String email;
}
