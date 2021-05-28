package com.example.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpUserDto {
    private Long account_id;
    private String username;
    private String password;
    private String email;
    private Integer age;
    private String gender;
    private String major;
    private Integer grade;
}
