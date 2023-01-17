package com.herewego.herewegoapi.repository;

import com.herewego.herewegoapi.common.Consts;
import com.herewego.herewegoapi.model.entity.Authorization;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorizationRepositoryTest {

    @Autowired
    AuthorizationRepository authorizationRepository;

    @BeforeEach
    public void setup() {
        authorizationRepository.save(Authorization.builder()
                .userId(Consts.USERID)
                .accessToken(Consts.ACCESSTOKEN)
                .build());
    }

    @AfterEach
    public void teardown() {
        authorizationRepository.deleteByUserId(Consts.USERID);
    }

    @Test
    void findByEmailAndAuthProvider() {
        Authorization authorization = authorizationRepository.findByUserId(Consts.USERID);

        Assertions.assertNotNull(authorization);
    }
}