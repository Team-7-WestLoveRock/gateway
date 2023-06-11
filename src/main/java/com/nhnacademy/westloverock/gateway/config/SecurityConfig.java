package com.nhnacademy.westloverock.gateway.config;

import com.nhnacademy.westloverock.gateway.auth.LoginLogSaveLogoutHandler;
import com.nhnacademy.westloverock.gateway.auth.LoginSuccessHandler;
import com.nhnacademy.westloverock.gateway.service.AccountService;
import com.nhnacademy.westloverock.gateway.service.CustomOAuth2UserService;
import com.nhnacademy.westloverock.gateway.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@EnableWebSecurity(debug = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(req -> req
                        .antMatchers("/minidooray/**").authenticated()
                        .anyRequest().permitAll())
                .formLogin(f -> f.successHandler(loginSuccessHandler()))
                .oauth2Login(o -> o.userInfoEndpoint()
                        .userService(customOAuth2UserService).and()
                        .defaultSuccessUrl("/minidooray")
                        .successHandler(loginSuccessHandler()))
                .logout(l -> l.logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler(null))
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/"))
                .csrf()
                    .and()
                .sessionManagement(s -> s.sessionFixation()
                        .newSession())
                .exceptionHandling()
                .and()
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public LogoutHandler logoutHandler(AccountService accountService) {
        return new LoginLogSaveLogoutHandler(accountService);
    }
}
