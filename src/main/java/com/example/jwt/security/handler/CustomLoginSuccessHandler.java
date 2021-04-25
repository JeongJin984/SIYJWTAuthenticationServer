package com.example.jwt.security.handler;

import com.example.jwt.dto.AccountContext;
import com.example.jwt.repository.LogOutUserRepository.LogOutUserRepository;
import com.example.jwt.security.util.jwt.ResponseToken;
import com.example.jwt.security.util.jwt.TokenConstant;
import com.example.jwt.security.util.jwt.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    LogOutUserRepository logOutUserRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        final AccountContext user = new AccountContext((String)authentication.getPrincipal(), null, authentication.getAuthorities());
        final String token = TokenUtils.generateJwtToken(user);

        Cookie cookie = new Cookie(TokenConstant.AUTH_HEADER, TokenConstant.TOKEN_TYPE + token);

        // expires in 7 days
        cookie.setMaxAge(7 * 24 * 60 * 60);

        // optional properties
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
    }
}
