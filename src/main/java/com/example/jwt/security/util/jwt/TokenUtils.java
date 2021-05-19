package com.example.jwt.security.util.jwt;

import com.example.jwt.dto.AccountContext;
import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@NoArgsConstructor
@Component
public class TokenUtils extends CreateTokenInfo {

    private Environment env;

    @Autowired
    public TokenUtils(Environment env) {
        this.env = env;
    }

    public String generateJwtToken(AccountContext context, int minute) {
        String secret = env.getProperty("token.secret");
        JwtBuilder builder = Jwts.builder()
                .setSubject(context.getUsername())
                .setHeader(createHeader())
                .setClaims(createClaims(context))
                .setExpiration(createExpireDate(minute))
                .signWith(SignatureAlgorithm.HS512, secret);

        return builder.compact();
    }


}
