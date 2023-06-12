package com.nhnacademy.westloverock.gateway.repository;

import com.nhnacademy.westloverock.gateway.domain.LoginSession;
import org.springframework.data.repository.CrudRepository;

public interface LoginSessionRedisRepository extends CrudRepository<LoginSession, String> {
    LoginSession findByUserId(String userId);
    void deleteByUserId(String userId);
}
