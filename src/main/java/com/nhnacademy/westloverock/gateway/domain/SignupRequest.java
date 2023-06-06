package com.nhnacademy.westloverock.gateway.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter @Setter
@ToString
public class SignupRequest implements Serializable {
    @NotEmpty
    @Length(max = 45)
    private String userId;
    @NotEmpty
    private String password;
    @Pattern(regexp = "[a-zA-Z가-힇]{1,60}", message = "문자를 입력해주세요")
    private String name;
    private String nickname;
    @Email
    @NotEmpty
    private String email;
    @Pattern(regexp = "^[0-9]*$")
    private String phoneNumber;
}
