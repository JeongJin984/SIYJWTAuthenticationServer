package com.example.jwt.kafka.producer;

import com.example.jwt.entity.account.Account;
import com.example.jwt.entity.account.authorization.AccountRole;
import com.example.jwt.kafka.dto.Field;
import com.example.jwt.kafka.dto.Schema;
import com.example.jwt.kafka.dto.account.KafkaUserDto;
import com.example.jwt.kafka.dto.account.UserPayload;
import com.example.jwt.kafka.dto.accountRole.AccountRolePayload;
import com.example.jwt.kafka.dto.accountRole.KafkaAccountRoleDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountRoleProducer {
    private final KafkaTemplate<String ,String> kafkaTemplate;

    List<Field> fieldList = Arrays.asList(
            new Field("int64", true, "id"),
            new Field("int64", true, "account_id"),
            new Field("int64", true, "role_id")
    );

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fieldList)
            .optional(false)
            .name("account_role")
            .build();

    public KafkaAccountRoleDto send(String topic, List<Long> accountRoleId) {
        AccountRolePayload payload = AccountRolePayload.builder()
                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .account_id(accountRoleId.get(0))
                .role_id(accountRoleId.get(1))
                .build();

        KafkaAccountRoleDto kafkaAccountRoleDto = new KafkaAccountRoleDto(schema, payload);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(kafkaAccountRoleDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonString);
        log.info("Kafka Producer data from the User microservice: " + kafkaAccountRoleDto);

        return kafkaAccountRoleDto;
    }
}
