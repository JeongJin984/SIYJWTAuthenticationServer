package com.example.jwt.security.handler;

import com.example.jwt.dto.AccountContext;
import com.example.jwt.repository.LogOutUserRepository.LogOutUserRepository;
import com.example.jwt.security.util.jwt.RefreshToken.RefreshTokenConstant;
import com.example.jwt.security.util.jwt.accesToken.ResponseToken;
import com.example.jwt.security.util.jwt.accesToken.TokenConstant;
import com.example.jwt.security.util.jwt.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    LogOutUserRepository logOutUserRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        final AccountContext user = new AccountContext((String)authentication.getPrincipal(), null, authentication.getAuthorities());
        final String accessToken = TokenUtils.generateJwtToken(user, 30);

        final AccountContext nullUser = new AccountContext("", null, authentication.getAuthorities());
        final String refreshToken = TokenUtils.generateJwtToken(nullUser, 60 * 24 * 7);

        Cookie cookie = new Cookie(TokenConstant.AUTH_HEADER, TokenConstant.TOKEN_TYPE + accessToken);
        Cookie cookie2 = new Cookie(RefreshTokenConstant.AUTH_HEADER, RefreshTokenConstant.TOKEN_TYPE + refreshToken);

        // expires in 7 days
        cookie.setMaxAge(7 * 24 * 60 * 60);

        // optional properties
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        // expires in 7 days
        cookie2.setMaxAge(7 * 24 * 60 * 60);

        // optional properties
        cookie2.setSecure(true);
        cookie2.setHttpOnly(true);
        cookie2.setPath("/");

        response.addCookie(cookie);
        response.addCookie(cookie2);

        ResponseToken token = new ResponseToken(TokenConstant.TOKEN_TYPE + accessToken,
                RefreshTokenConstant.TOKEN_TYPE + refreshToken);

        response.getWriter().write(objectMapper.writeValueAsString(user));
    }
}
