package com.herewego.herewegoapi.controller;

import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.model.entity.User;
import com.herewego.herewegoapi.model.request.JoinRequestVO;
import com.herewego.herewegoapi.repository.UserRepository;
import com.herewego.herewegoapi.response.ApiResponse;
import com.herewego.herewegoapi.security.CustomUserDetails;
import com.herewego.herewegoapi.security.oauth.CustomOAuth2UserService;
import com.herewego.herewegoapi.service.UserService;
import com.herewego.herewegoapi.vo.RegisterUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    @PostMapping(value = "/join")
    public Object registerUser(
            @RequestHeader(value = "Access-Token") String accessToken,
            @RequestHeader(value = "Refresh-Token") String refreshToken,
            @RequestBody JoinRequestVO joinVO) throws ForwardException {
        LOGGER.debug("GET 요청 /v1/join");
        return customOAuth2UserService.login(accessToken, refreshToken, joinVO);
    }

    @GetMapping(value = "/users")
    public Object getUserInformation(
            @RequestHeader(required = false, value = "UserId") String userId,
            @RequestHeader(required = false, value = "Email") String email,
            @RequestHeader(required = false, value = "Authorization") String accountToken) {

        return userService.getUserInformation(userId, accountToken);
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
