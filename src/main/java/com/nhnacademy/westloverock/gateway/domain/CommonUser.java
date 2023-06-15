package com.nhnacademy.westloverock.gateway.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class CommonUser implements UserDetails, OAuth2User {
    private String username;
    private HashMap<String, Object> attributes;
    public CommonUser(String username, Map<String, Object> attributes) {
        this.username = username;
        this.attributes = new LinkedHashMap<>(attributes);
    }
    @Override
    public String getName() {
        return this.attributes.getOrDefault("name", "").toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (attributes.get("status").equals("DORMANCY")) {
            return Collections.emptyList();
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getPassword() {
        return this.attributes.get("password").toString();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
