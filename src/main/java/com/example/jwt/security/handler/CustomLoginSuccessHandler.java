package com.example.jwt.security.handler;

import com.example.jwt.dto.AccountContext;
import com.example.jwt.security.util.jwt.RefreshToken.RefreshTokenConstant;
import com.example.jwt.security.util.jwt.accesToken.ResponseToken;
import com.example.jwt.security.util.jwt.accesToken.TokenConstant;
import com.example.jwt.security.util.jwt.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor
@Service
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private TokenUtils tokenUtils;

    @Autowired
    public CustomLoginSuccessHandler(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        final AccountContext user = new AccountContext((String)authentication.getPrincipal(), null, authentication.getAuthorities());
        final String accessToken = tokenUtils.generateJwtToken(user, 60 * 24 * 7);

        final AccountContext nullUser = new AccountContext("", null, authentication.getAuthorities());
        final String refreshToken = tokenUtils.generateJwtToken(nullUser, 60 * 24 * 7);

        Cookie cookie = new Cookie(TokenConstant.AUTH_HEADER, accessToken);
        Cookie cookie2 = new Cookie(RefreshTokenConstant.AUTH_HEADER, refreshToken);

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
