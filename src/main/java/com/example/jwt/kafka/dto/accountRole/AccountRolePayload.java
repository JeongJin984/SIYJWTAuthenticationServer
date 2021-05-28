package com.example.jwt.kafka.dto.accountRole;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class AccountRolePayload {
    private Long id;
    private Long account_id;
    private Long role_id;
}
