package com.example.jwt.security.util.jwt.RefreshToken;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenConstant {
    public static final String AUTH_HEADER = "Refresh";
    public static final String TOKEN_TYPE = "BEARER";
}
