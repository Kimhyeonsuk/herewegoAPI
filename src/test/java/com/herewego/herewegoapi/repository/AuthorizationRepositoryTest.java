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
                .email(Consts.EMAIL)
                .authProvider(Consts.AUTHPROVIDER)
                .refreshToken(Consts.REFRESHTOKEN)
                .accessToken(Consts.ACCESSTOKEN)
                .build());
    }

    @AfterEach
    public void teardown() {
        authorizationRepository.deleteByEmailAndAuthProvider(Consts.EMAIL, Consts.AUTHPROVIDER);
    }

    @Test
    void findByEmailAndAuthProvider() {
        Authorization authorization = authorizationRepository.findByEmailAndAuthProvider(Consts.EMAIL, Consts.AUTHPROVIDER);

        Assertions.assertNotNull(authorization);
    }
}