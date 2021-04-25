package com.example.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountParamDto {
    private String username;
    private String password;
    private Boolean rememberMe;

    public AccountParamDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
