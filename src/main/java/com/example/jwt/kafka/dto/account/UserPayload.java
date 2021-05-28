package com.example.jwt.kafka.dto.account;

import com.example.jwt.entity.account.info.Gender;
import com.example.jwt.entity.account.info.Major;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserPayload {
    private Long account_id;
    private String username;
    private String email;
    private Integer age;
    private String password;
    private Integer gender;
    private String major;
    private Integer grade;
}
