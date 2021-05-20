package com.example.jwt.kafka.dto.user.object;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payload {
    private String username;
    private String email;
    private Integer age;
    private String password;
}
