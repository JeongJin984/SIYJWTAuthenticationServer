package com.example.jwt.dto;

import com.example.jwt.entity.account.info.Gender;
import com.example.jwt.entity.account.info.Info;
import com.example.jwt.entity.account.info.Major;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
public class UserResponse {
    String username;
    String email;
    Integer age;
    String major;
    String gender;
    Integer grade;
}
