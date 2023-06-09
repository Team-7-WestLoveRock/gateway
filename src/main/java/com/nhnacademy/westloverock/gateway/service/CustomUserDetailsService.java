package com.nhnacademy.westloverock.gateway.service;

import com.nhnacademy.westloverock.gateway.domain.AccountDTO;
import com.nhnacademy.westloverock.gateway.domain.CommonUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDTO account = accountService.fetchByUserId(username);
        if (Objects.isNull(account)) throw new UsernameNotFoundException(username + " not found");

        return new CommonUser(account.getUserId(), Map.of("password", account.getPassword(), "email", account.getEmail()));
//        return new User(account.getUserId(),
//                account.getPassword(),
//                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
