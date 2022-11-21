package com.herewego.herewegoapi.controller;

import com.herewego.herewegoapi.response.ApiResponse;
import com.herewego.herewegoapi.vo.RegisterUserVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class UserController {

    @PostMapping(value = "/users")
    public Object registerUser(
            @RequestHeader(value = "Account-Token") String accountToken,
            @RequestHeader(value = "Account-Id") String accountId,
            @RequestBody RegisterUserVO registerUserVO) {

        return ApiResponse.ok();
    }

    @GetMapping(value = "/users")
    public Object getUserInformation(
            @RequestHeader(value = "Account-Token") String accountToken,
            @RequestHeader(value = "Account-Id") String accountId,
            @RequestBody RegisterUserVO registerUserVO)  {

        return ApiResponse.ok();
    }

    @PutMapping(value = "/users/gameunit")
    public Object changeUserGameUnit(
            @RequestHeader(value = "Account-Token") String accountToken,
            @RequestHeader(value = "Account-Id") String accountId,
            @RequestBody RegisterUserVO registerUserVO) {

        return ApiResponse.ok();
    }
}
