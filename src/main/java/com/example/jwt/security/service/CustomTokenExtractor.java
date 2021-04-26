package com.example.jwt.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Service
public class CustomTokenExtractor{

    public String getTokenFromRequest(HttpServletRequest request, String token_key_jwt) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(token_key_jwt))
                .findFirst()
                .map(Cookie::getValue).orElse(null);
    }

}