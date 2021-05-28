package com.example.jwt.kafka.dto.accountRole;

import com.example.jwt.kafka.dto.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KafkaAccountRoleDto {
    private Schema schema;
    private AccountRolePayload payload;
}
