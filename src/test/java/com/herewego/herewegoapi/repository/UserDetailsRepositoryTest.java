package com.herewego.herewegoapi.repository;

import com.herewego.herewegoapi.common.Consts;
import com.herewego.herewegoapi.model.entity.Authorization;
import com.herewego.herewegoapi.model.entity.UserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDetailsRepositoryTest {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @BeforeEach
    public void setup() {
        userDetailsRepository.save(UserDetails.builder()
                .email(Consts.EMAIL)
                .authProvider(Consts.AUTHPROVIDER)
                .role(Consts.ROLE)
                .favorites(Consts.FAVORITETEAM)
                .build());
    }

    @AfterEach
    public void teardown() {
        userDetailsRepository.deleteByEmailAndAuthProvider(Consts.EMAIL, Consts.AUTHPROVIDER);
    }

    @Test
    void findByEmailAndAuthProviderTest() {
        UserDetails userDetails = userDetailsRepository.findByEmailAndAuthProvider(Consts.EMAIL, Consts.AUTHPROVIDER);

        Assertions.assertNotNull(userDetails);
    }
}