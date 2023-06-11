package com.nhnacademy.westloverock.gateway.repository;

import com.nhnacademy.westloverock.gateway.domain.LoginSession;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataRedisTest
class LoginSessionRedisRepositoryTest {
    @Autowired
    private LoginSessionRedisRepository repository;

    @Test
    @Order(1)
    @DisplayName("등록 - 정상")
    void test1() {
        LoginSession loginSession = new LoginSession("user1", "127.0.0.1");
        LoginSession loginSession1 = repository.save(loginSession);
        assertThat(loginSession1.getUserId()).isEqualTo(repository.findById(loginSession.getUserId()).get().getUserId());
    }

    @Test
    @Order(2)
    @DisplayName("삭제 - 정상")
    void test2() {
        LoginSession actual = new LoginSession("user1", "127.0.0.1");
        LoginSession expect = repository.save(actual);

        repository.deleteById("user1");
        assertThat(repository.findById(expect.getUserId())).isEmpty();
    }
}