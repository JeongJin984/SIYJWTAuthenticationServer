package com.example.jwt.api;

import com.example.jwt.dto.AccountContext;
import com.example.jwt.entity.account.Account;
import com.example.jwt.security.service.CustomTokenExtractor;
import com.example.jwt.security.util.jwt.GetTokenInfo;
import com.example.jwt.security.util.jwt.TokenConstant;
import com.example.jwt.security.util.jwt.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.CharBuffer;
import java.util.HashMap;

import org.springframework.security.jwt.codec.Codecs;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;

import static com.example.jwt.security.util.jwt.GetTokenInfo.getUserName;

@RestController
@RequiredArgsConstructor
public class JwtApiController {

    private final CustomTokenExtractor tokenExtractor;

    @GetMapping(value = "/api/refresh")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String username = getUserName(tokenExtractor.getTokenFromRequest(request).substring(6));

        final String new_token = TokenUtils.generateJwtToken(new AccountContext(username, null, null));

        return new ResponseEntity<>(new_token, HttpStatus.OK);
    }
}
