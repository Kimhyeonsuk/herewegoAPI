package com.herewego.herewegoapi.controller;

import com.herewego.herewegoapi.common.GameUnitValidator;
import com.herewego.herewegoapi.exceptions.ErrorCode;
import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.model.entity.User;
import com.herewego.herewegoapi.model.request.ChangeGameUnitDTO;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    GameUnitValidator gameUnitValidator;


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
            @RequestHeader(value = "UserId") String userId,
            @RequestHeader(value = "Authorization") String accountToken) {

        return userService.getUserInformation(userId, accountToken);
    }

    @PutMapping(value = "/users/gameunit")
    public Object changeUserGameUnit(
            @RequestHeader(value = "UserId") String userId,
            @RequestHeader(value = "Authorization") String accountToken,
            @RequestBody ChangeGameUnitDTO changeGameUnitDTO, BindingResult bindingResult) throws ForwardException {
        gameUnitValidator.validate(changeGameUnitDTO,bindingResult);
        if (bindingResult.hasErrors()) {
            LOGGER.error("Bad Request : Invalid String");
            throw new ForwardException(ErrorCode.RC400000,"Invlid GameUnit String");
        }
        return ApiResponse.ok();
    }
}
