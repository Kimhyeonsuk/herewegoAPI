package com.herewego.herewegoapi.controller;

import com.herewego.herewegoapi.security.jwt.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Value("${spring.profiles.active}")
    String profiles;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testMethod () {
        LOGGER.debug("/test uri 요청 profile: {}", profiles);
        return profiles;
    }
}
