package com.example.jwt.security.util.jwt;

import com.example.jwt.dto.AccountContext;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateTokenInfo {

    @Value("${token.secret}")
    private static String secretKey;

    protected Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    protected Map<String, Object> createClaims(AccountContext context) {
        // 공개 클레임에 사용자의 이름과 이메일을 설정하여 정보를 조회할 수 있다.
        Map<String, Object> claims = new HashMap<>();

        claims.put("name", context.getUsername());
        claims.put("role", context.getAuthorities());

        return claims;
    }

    protected Date createExpireDate(Integer minute) {
        // 토큰 만료시간은 30일으로 설정
        Date date = new Date();
        long t = date.getTime();
        long expirationTime = (long)minute * 60L * 1000L;
        Date expirationDate = new Date(t + expirationTime); // set 5 seconds
        return expirationDate;
    }
}
