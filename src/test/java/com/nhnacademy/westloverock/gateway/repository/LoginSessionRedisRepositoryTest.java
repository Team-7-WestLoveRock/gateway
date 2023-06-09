package com.nhnacademy.westloverock.gateway.repository;

import com.nhnacademy.westloverock.gateway.domain.LoginSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("dev")
@DataRedisTest
class LoginSessionRedisRepositoryTest {
    @Autowired
    private LoginSessionRedisRepository repository;

    @Test
    void test1() {
        LoginSession loginSession = new LoginSession("user1", "127.0.0.1");

        LoginSession loginSession1 = repository.save(loginSession);

        assertThat(loginSession1.getUserId()).isEqualTo(repository.findById(loginSession.getUserId()).get().getUserId());
    }

}