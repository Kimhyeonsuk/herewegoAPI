package com.herewego.herewegoapi.security.jwt;

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
        if (!"/test".equals(path) && !"/health".equals(path) && !"/favicon.ico".equals(path)) {
            String token = parseBearerToken(request);
            LOGGER.debug("Authorization: {}", token);

            // Validation Access Token
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.debug(authentication.getName() + "의 인증정보 저장");
            } else {
                LOGGER.debug("유효한 JWT 토큰이 없습니다.");
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
