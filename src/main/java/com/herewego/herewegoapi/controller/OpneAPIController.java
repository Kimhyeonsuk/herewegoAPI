package com.herewego.herewegoapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/openapi")
public class OpneAPIController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpneAPIController.class);

    private static final String HEADER_RAPIDAPI_KEY = "c5f7cfe6abmsh29132c5f54915f6p16b75djsnd3a4891c19cb"; //TODO: 파라미터 스토어 저장
    private static final String HEADER_RAPIDAPI_HOST = "api-football-v1.p.rapidapi.com"; // TODO: 파라미터 스토어 저장

}
