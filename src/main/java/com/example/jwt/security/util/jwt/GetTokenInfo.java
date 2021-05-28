package com.example.jwt.security.util.jwt;

import com.example.jwt.entity.account.authorization.Role;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.jwt.codec.Codecs;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;

@Slf4j
@Component
public class GetTokenInfo {

    @Value("${token.secret}")
    private String secretKey;

    private Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
    }

    public String getUsernameFromValidToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("username");
    }

    private Role getRoleFromValidToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (Role) claims.get("role");
    }

    public boolean isValidToken(String token) {
        String subject = null;
        try {
            subject = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody()
                    .getSubject();
        } catch (JwtException | NullPointerException exception) {
            return false;
        }
        return true;
    }

    private String decode(String token) {
        int firstPeriod = token.indexOf(46);
        int lastPeriod = token.lastIndexOf(46);
        byte[] claims = null;
        if (firstPeriod > 0 && lastPeriod > firstPeriod) {
            CharBuffer buffer = CharBuffer.wrap(token, 0, firstPeriod);
            buffer.limit(lastPeriod).position(firstPeriod + 1);
            claims = Codecs.b64UrlDecode(buffer);
            boolean emptyCrypto = lastPeriod == token.length() - 1;
            byte[] crypto;
            if (emptyCrypto) {
                crypto = new byte[0];
            } else {
                buffer.limit(token.length()).position(lastPeriod + 1);
                crypto = Codecs.b64UrlDecode(buffer);
            }
        }
        return Codecs.utf8Decode(claims);
    }

    public String getUserName(String token) {
        String username = null;

        String result = decode(token);
        String[] split = result.replaceAll("[^a-z|0-9|,|:]", "").split("[,|:]");

        for (int i = 0; i < split.length; i++) {
            if(split[i].equals("name")) {
                username = split[i+1];
            };
        }
        return username;
    }
}
