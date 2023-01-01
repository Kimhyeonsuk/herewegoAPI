package com.herewego.herewegoapi.service;

import com.herewego.herewegoapi.model.response.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void getUserVOTest() {
        //UserVO userVO = userService.getUserInformation("sukrrard97@gmail.com");

        //System.out.println("test");
    }
}