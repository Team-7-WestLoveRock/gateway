package com.nhnacademy.westloverock.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: Repository에서 user 가져오기

        // Temp Users
        List<Map<String, String>> users = new ArrayList<>();
        users.add(Map.of("admin", passwordEncoder.encode("admin")));
        users.add(Map.of("user", passwordEncoder.encode("user")));

        Map<String, String> user = users.stream()
                .filter(m -> m.containsKey(username))
                .findAny()
                .orElseThrow();
        return new User(username, user.get(username),
            Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }
}
