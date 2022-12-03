package com.herewego.herewegoapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${spring.profiles.active}")
    String profiles;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testMethod () {
        return profiles;
    }
}
