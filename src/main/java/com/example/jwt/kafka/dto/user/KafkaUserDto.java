package com.example.jwt.kafka.dto.user;

import com.example.jwt.kafka.dto.user.object.Payload;
import com.example.jwt.kafka.dto.user.object.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class KafkaUserDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
