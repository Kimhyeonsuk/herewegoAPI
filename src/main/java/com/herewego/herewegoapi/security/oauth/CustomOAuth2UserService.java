package com.herewego.herewegoapi.security.oauth;

import com.herewego.herewegoapi.common.AuthProvider;
import com.herewego.herewegoapi.common.UserRole;
import com.herewego.herewegoapi.exceptions.ErrorCode;
import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.exceptions.OAuthProcessingException;
import com.herewego.herewegoapi.model.entity.User;
import com.herewego.herewegoapi.repository.UserRepository;
import com.herewego.herewegoapi.security.CustomUserDetails;
import com.herewego.herewegoapi.security.oauth.user.OAuth2UserInfo;
import com.herewego.herewegoapi.security.oauth.user.OAuth2UserInfoFactory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    UserRepository userRepository;

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

    // 획득한 유저정보를 Java Model과 맵핑하고 프로세스 진행
    private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws OAuthProcessingException {
        AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());

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
        }
        return CustomUserDetails.create(user, oAuth2User.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo, AuthProvider authProvider) {
        User user = User.builder()
                .email(userInfo.getEmail())
                .img(userInfo.getImageUrl())
                .role(UserRole.USER)
                .authProvider(authProvider)
                .build();
        return userRepository.save(user);
    }
}
