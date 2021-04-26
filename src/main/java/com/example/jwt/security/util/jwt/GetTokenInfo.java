package com.example.jwt.security.util.jwt;

import com.example.jwt.entity.account.Role;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.jwt.codec.Codecs;

import javax.xml.bind.DatatypeConverter;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GetTokenInfo {

    private final static String secretKey = CreateTokenInfo.secretKey;

    private static Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .parseClaimsJws(token).getBody();
    }

    public static String getUsernameFromValidToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("username");
    }

    private static Role getRoleFromValidToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (Role) claims.get("role");
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            return true;

        } catch (ExpiredJwtException exception) {
            return false;
        } catch (JwtException exception) {
            return false;
        } catch (NullPointerException exception) {
            return false;
        }
    }

    private static String decode(String token) {
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

    public static String getUserName(String token) {
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
