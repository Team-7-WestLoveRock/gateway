package com.nhnacademy.westloverock.gateway.service;

import com.nhnacademy.westloverock.gateway.domain.AccountDTO;
import com.nhnacademy.westloverock.gateway.domain.CommonUser;
import com.nhnacademy.westloverock.gateway.exception.DormancyUserException;
import com.nhnacademy.westloverock.gateway.exception.WithdrawalUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDTO account = accountService.fetchByUserId(username);
        if (Objects.isNull(account)) {
            throw new UsernameNotFoundException(username + " not found");
        }

        if (account.getState().equals("WITHDRAWAL")) {
            throw new WithdrawalUserException(account.getUserId() + "는 탈퇴한 유저입니다.");
        }

        Map<String, Object> userAdditionalInfo = new LinkedHashMap<>();

        userAdditionalInfo.put("password", account.getPassword());
        userAdditionalInfo.put("email", account.getEmail());
        userAdditionalInfo.put("status", account.getState());

        return new CommonUser(account.getUserId(), userAdditionalInfo);
    }
}
