package org.iptime.yoon.blog.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.iptime.yoon.blog.security.auth.AuthUser;
import org.iptime.yoon.blog.security.auth.JwtUser;
import org.iptime.yoon.blog.security.dto.LogInSuccessResponse;
import org.iptime.yoon.blog.security.exception.InvalidRefreshTokenException;
import org.iptime.yoon.blog.security.jwt.JwtManager;
import org.iptime.yoon.blog.user.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * @author rival
 * @since 2023-08-17
 */
@Slf4j
public class JwtRefreshFilter implements Filter {


    private final JwtManager jwtManager;
    private final String refreshURI;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper;

    public JwtRefreshFilter(String refreshURI, RefreshTokenService refreshTokenService, JwtManager jwtManager, ObjectMapper objectMapper) {
        this.refreshURI = refreshURI;
        this.refreshTokenService = refreshTokenService;
        this.jwtManager = jwtManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (refreshURI.equals(httpRequest.getRequestURI())) {
            String refreshToken = null;
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("refreshToken".equals(cookie.getName())) {
                        refreshToken = cookie.getValue();
                    }
                }
            }

            if (refreshToken == null) throw new InvalidRefreshTokenException(null);

            JwtUser jwtUser = refreshTokenService.validateTokenAndGetJwtUser(refreshToken);

            String token = jwtManager.createToken(jwtUser);
            LogInSuccessResponse body = LogInSuccessResponse.builder()
                .token(token)
                .username(jwtUser.getUsername())
                .build();

            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            httpResponse.getOutputStream().write(objectMapper.writeValueAsBytes(body));

            log.info("Jwt refreshed {}", token);

        } else {
            chain.doFilter(request, response);
        }
    }
}
