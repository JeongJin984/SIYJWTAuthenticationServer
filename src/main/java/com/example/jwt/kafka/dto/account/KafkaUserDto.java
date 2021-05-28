package com.example.jwt.kafka.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.example.jwt.kafka.dto.Schema;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class KafkaUserDto implements Serializable {
    private Schema schema;
    private UserPayload payload;
}
