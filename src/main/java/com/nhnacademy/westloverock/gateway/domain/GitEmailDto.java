package com.nhnacademy.westloverock.gateway.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitEmailDto {
    private String email;
    private Boolean primary;
    private Boolean verified;
    private String visibility;
}