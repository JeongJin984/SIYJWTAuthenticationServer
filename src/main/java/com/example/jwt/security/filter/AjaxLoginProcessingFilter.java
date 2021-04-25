package com.example.jwt.security.filter;

import com.example.jwt.dto.AccountParamDto;
import com.example.jwt.entity.account.Account;
import com.example.jwt.security.util.ajax.AjaxAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.InputMismatchException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter  {

    private ObjectMapper objectMapper = new ObjectMapper();

    public AjaxLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        if(!isAjax(request)) {
            throw new AuthenticationServiceException("Authentication not Supported");
        }

        AccountParamDto accountParam = objectMapper.readValue(request.getReader(), AccountParamDto.class);

        if(!StringUtils.hasText(accountParam.getUsername()) || !StringUtils.hasText(accountParam.getPassword())) {
            throw new IllegalArgumentException("Username or Password is required");
        }

        AjaxAuthenticationToken token = new AjaxAuthenticationToken(accountParam.getUsername(), accountParam.getPassword());

        return this.getAuthenticationManager().authenticate(token);
    }

    private boolean isAjax(HttpServletRequest request) {
        String header = request.getHeader("X-Request-With");
        if("XMLHttpRequest".equals(request.getHeader("X-Request-With"))) {
            return true;
        }
        return false;
    }
}
