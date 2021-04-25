package com.example.jwt.security.util.jwt;

import com.example.jwt.dto.AccountContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.Date;

@NoArgsConstructor
public class TokenUtils extends CreateTokenInfo {

    public static String generateJwtToken(AccountContext context) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(context.getUsername())
                .setHeader(createHeader())
                .setClaims(createClaims(context))
                .setExpiration(createExpireDateForOneYear(5))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()));

        return builder.compact();
    }


}
