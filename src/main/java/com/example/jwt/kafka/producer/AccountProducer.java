package com.example.jwt.kafka.producer;

import com.example.jwt.entity.account.Account;
import com.example.jwt.kafka.dto.user.KafkaUserDto;
import com.example.jwt.kafka.dto.user.object.Field;
import com.example.jwt.kafka.dto.user.object.Payload;
import com.example.jwt.kafka.dto.user.object.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountProducer {

    private final KafkaTemplate<String ,String> kafkaTemplate;
    private final PasswordEncoder encoder;

    List<Field> fieldList = Arrays.asList(
            new Field("string", true, "username"),
            new Field("string", true, "password"),
            new Field("string", true, "email"),
            new Field("int32", true, "age")
    );
    Schema schema = Schema.builder()
            .type("struct")
            .fields(fieldList)
            .optional(false)
            .name("account")
            .build();

    public KafkaUserDto send(String topic, Account account) {
        Payload payload = Payload.builder()
                .username(account.getUsername())
                .password(encoder.encode(account.getPassword()))
                .email(account.getUsername())
                .age(account.getAge())
                .build();

        KafkaUserDto kafkaUserDto = new KafkaUserDto(schema, payload);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(kafkaUserDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonString);
        log.info("Kafka Producer data from the User microservice: " + kafkaUserDto);

        return kafkaUserDto;
    }
}
