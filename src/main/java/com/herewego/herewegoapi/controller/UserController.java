package com.herewego.herewegoapi.controller;

import com.herewego.herewegoapi.model.entity.User;
import com.herewego.herewegoapi.repository.UserRepository;
import com.herewego.herewegoapi.response.ApiResponse;
import com.herewego.herewegoapi.security.CustomUserDetails;
import com.herewego.herewegoapi.service.UserService;
import com.herewego.herewegoapi.vo.RegisterUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @PostMapping(value = "/users")
    public Object registerUser(
            @RequestHeader(value = "Account-Token") String accountToken,
            @RequestHeader(value = "Account-Id") String accountId,
            @RequestBody RegisterUserVO registerUserVO) {

        return ApiResponse.ok();
    }

    //Parameter로 들어오는 authorization은 재인증 시에 redirect url에 토큰을 담아서 요청하기 때문에 받아온다.
    @GetMapping(value = "/users")
    public Object getUserInformation(
            @RequestHeader(required = false, value = "Authorization") String accountToken,
            @RequestHeader(required = false, value = "Email") String email,
            @RequestParam(required = false, value = "authorization") String accountTokenParam) {

        return userService.getUserInformation(email, accountToken, accountTokenParam);
    }

    @PutMapping(value = "/users/gameunit")
    public Object changeUserGameUnit(
            @RequestHeader(value = "Account-Token") String accountToken,
            @RequestHeader(value = "Account-Id") String accountId,
            @RequestBody RegisterUserVO registerUserVO) {

        return ApiResponse.ok();
    }

    @GetMapping("/useres/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@AuthenticationPrincipal CustomUserDetails user) {
        return userRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("not found user"));
    }
}
