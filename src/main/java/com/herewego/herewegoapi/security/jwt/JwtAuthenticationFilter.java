package com.herewego.herewegoapi.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herewego.herewegoapi.exceptions.ErrorCode;
import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        LOGGER.debug("path : {}", path);

        ObjectMapper mapper = new ObjectMapper();

        if (!"/test".equals(path) && !"/health".equals(path) && !"/favicon.ico".equals(path) && !"/v1/join".equals(path)) {
            String token = parseBearerToken(request);
            LOGGER.debug("Authorization: {}", token);

            // Validation Access Token
            try {
                if (tokenProvider.validateToken(token)) {
                    Authentication authentication = tokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    LOGGER.debug(authentication.getName() + "의 인증정보 저장");
                }
            } catch (ForwardException e) {
                String errorString = null;
                try {
                    LOGGER.debug("error 메시지: {}", e.getResponseMessage());
                    errorString = mapper.writeValueAsString(ApiResponse.error(response, e));
                } catch (ForwardException ex) {
                    ex.printStackTrace();
                }
                response.getWriter().write(errorString);
            }
        }

        filterChain.doFilter(request, response);
        return;
    }

    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        else {
            LOGGER.debug("request 파싱 실패, uri param 파싱, 토큰 {}", bearerToken);

            bearerToken = request.getParameter("authorization");
            if (StringUtils.hasText(bearerToken)) {
                LOGGER.debug("request 파싱 성공, 토큰 :{}", bearerToken);
                return bearerToken;
            }
        }
        LOGGER.debug("request 파싱 실패, response 파싱 실패, 토큰 {}", bearerToken);
        return null;
    }
}
