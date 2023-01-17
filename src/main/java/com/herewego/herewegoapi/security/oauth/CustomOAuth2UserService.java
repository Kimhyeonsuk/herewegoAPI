package com.herewego.herewegoapi.security.oauth;

import com.herewego.herewegoapi.common.AuthProvider;
import com.herewego.herewegoapi.common.UserRole;
import com.herewego.herewegoapi.common.WebClientBuilderManager;
import com.herewego.herewegoapi.exceptions.ErrorCode;
import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.exceptions.OAuthProcessingException;
import com.herewego.herewegoapi.model.entity.Authorization;
import com.herewego.herewegoapi.model.entity.User;
import com.herewego.herewegoapi.model.entity.UserDetails;
import com.herewego.herewegoapi.model.request.JoinRequestVO;
import com.herewego.herewegoapi.model.response.GoogleOauthGetUserInfoResponseVO;
import com.herewego.herewegoapi.model.response.JoinResponseVO;
import com.herewego.herewegoapi.repository.AuthorizationRepository;
import com.herewego.herewegoapi.repository.UserDetailsRepository;
import com.herewego.herewegoapi.repository.UserRepository;
import com.herewego.herewegoapi.security.CustomUserDetails;
import com.herewego.herewegoapi.security.jwt.JwtTokenProvider;
import com.herewego.herewegoapi.security.oauth.user.OAuth2UserInfo;
import com.herewego.herewegoapi.security.oauth.user.OAuth2UserInfoFactory;
import com.herewego.herewegoapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Optional;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    private static final String OAUTH2_GET_USERINFO_URI = "https://www.googleapis.com/oauth2/v1/tokeninfo?id_token=";
    private static final String GOOGLE_ISSUER = "https://accounts.google.com";

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorizationRepository authorizationRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    WebClientBuilderManager webClientBuilderManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserService userService;

    // OAuth2UserRequest에 있는 Access Token으로 유저정보 get
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return process(oAuth2UserRequest, oAuth2User);
        } catch (OAuthProcessingException e) {
            e.printStackTrace();
        return null;
    }
}

    @Transactional
    public JoinResponseVO login(String accessToken, String refreshToken, JoinRequestVO joinVO) throws ForwardException { //로그인 로직 모두 처리하는 메서드
        //google로부터 유저정보 받아서 db에 저장
        User user = getGoogleUserInfo(accessToken, refreshToken, joinVO);
        LOGGER.debug("Google User Email is {}", user.getEmail());

        //jwt token 발급
        String accessJwtToken = tokenProvider.createJWTAccessToken(user);
        String refreshJwtToken = tokenProvider.createRefreshJWTToken(user);

        Optional<User> userOptional = userRepository.findByUserId(user.getUserId());
        if (userOptional.isPresent()) {		// 이미 가입된 경우
            user = userOptional.get();
            updateAuthroization(user, accessJwtToken, refreshJwtToken);
        } else {			// 가입되지 않은 경우
            userRepository.save(user);
            createAuthroization(user, accessJwtToken, refreshJwtToken);
            createUserDetails(user);
        }

        return JoinResponseVO.builder()
                .jwtAccessToken(accessJwtToken)
                .jwtRefreshToken(refreshJwtToken)
                .build();
    }

    // 획득한 유저정보를 Java Model과 맵핑하고 프로세스 진행
    //TODO : DEPRECATED
    private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws OAuthProcessingException {
            AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
            OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());

        LOGGER.debug("accessToken은 : {}", oAuth2UserRequest.getAccessToken().toString());

        if (userInfo.getEmail().isEmpty()) {
            throw new OAuthProcessingException("Email not found from OAuth2 provider");
        }
        Optional<User> userOptional = userRepository.findByEmail(userInfo.getEmail());
        User user;

        if (userOptional.isPresent()) {		// 이미 가입된 경우
            user = userOptional.get();
            if (authProvider != user.getAuthProvider()) {
                throw new OAuthProcessingException("Wrong Match Auth Provider");
            }
        } else {			// 가입되지 않은 경우
            user = createUser(userInfo, authProvider);
            createAuthorization(userInfo, authProvider);
        }
        return CustomUserDetails.create(user, oAuth2User.getAttributes());
    }

    //TODO : DEPRECATED
    private User createUser(OAuth2UserInfo userInfo, AuthProvider authProvider) {
        User user = User.builder()
                .email(userInfo.getEmail())
                .img(userInfo.getImageUrl())
                .name(userInfo.getName())
                .role(UserRole.USER)
                .authProvider(authProvider)
                .build();
        return userRepository.save(user);
    }

    //TODO : DEPRECATED
    private void createAuthorization(OAuth2UserInfo userInfo, AuthProvider authProvider) {
//        Authorization authorization= authorizationRepository.findByEmailAndAuthProvider(userInfo.getEmail(), authProvider);
//        if (ObjectUtils.isEmpty(authorization)) {
//            authorizationRepository.save(Authorization.builder()
//                    .email(userInfo.getEmail())
//                    .authProvider(authProvider)
//                    .build());
//        }
    }

    private void createUserDetails(User user){
        UserDetails userDetails= userDetailsRepository.findByUserId(user.getUserId());
        if (ObjectUtils.isEmpty(userDetails)) {
            userDetailsRepository.save(UserDetails.builder()
                    .userId(user.getUserId())
                    .build());
        }
    }

    private void createAuthroization(User user, String accessToken, String refreshToken){
        Authorization authorization= authorizationRepository.findByUserId(user.getUserId());
        if (ObjectUtils.isEmpty(authorization)) {
            authorizationRepository.save(Authorization.builder()
                    .userId(user.getUserId())
                    .authProvider(user.getAuthProvider())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build());
        }
    }

    private void updateAuthroization(User user, String accessToken, String refreshToken){
        authorizationRepository.updateAccessToken(user.getUserId(), accessToken);
        authorizationRepository.updateRefreshToken(user.getUserId(), refreshToken);
    }

    private User getGoogleUserInfo(String accessToken, String refreshToken, JoinRequestVO joinVO) throws ForwardException {
        GoogleOauthGetUserInfoResponseVO googleOauthGetUserInfoResponseVO = webClientBuilderManager
                .makeCommonWebclientBuilder()
                .build()
                .get()
                .uri(OAUTH2_GET_USERINFO_URI + accessToken)
                .retrieve()
                .bodyToMono(GoogleOauthGetUserInfoResponseVO.class)
                .block(Duration.ofSeconds(60L));

        User user = User.builder()
                .email(googleOauthGetUserInfoResponseVO.getEmail())
                .userId(googleOauthGetUserInfoResponseVO.getUser_id())
                .role(UserRole.USER)
                .authProvider(googleOauthGetUserInfoResponseVO.getIssuer().equals(GOOGLE_ISSUER)?AuthProvider.GOOGLE:AuthProvider.NONE)
                .build();

        if (validateGoogleUserInfo(user, joinVO)) {
            user.setName(joinVO.getName());
            user.setImg(joinVO.getImage());
            return user;
        }
        throw new ForwardException(ErrorCode.RC400000, "Invalid User Information");
    }

    private boolean validateGoogleUserInfo(User user, JoinRequestVO joinVO){
        if (!user.getEmail().equals(joinVO.getEmail())) {
            LOGGER.debug("Invalid Email");
            return false;
        }

        if(!user.getAuthProvider().equals(joinVO.getAuthProvider())) {
            LOGGER.debug("Invalid Auth Provder");
            return false;
        }

        return true;
    }
}
