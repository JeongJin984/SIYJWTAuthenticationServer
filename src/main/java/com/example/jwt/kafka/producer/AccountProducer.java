package com.example.jwt.kafka.producer;

import com.example.jwt.converter.GenderAttributeConverter;
import com.example.jwt.dto.SignUpUserDto;
import com.example.jwt.entity.account.Account;
import com.example.jwt.entity.account.info.Gender;
import com.example.jwt.kafka.dto.account.KafkaUserDto;
import com.example.jwt.kafka.dto.Field;
import com.example.jwt.kafka.dto.account.UserPayload;
import com.example.jwt.kafka.dto.Schema;
import com.example.jwt.kafka.dto.account.UserPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountProducer {

    private final KafkaTemplate<String ,String> kafkaTemplate;
    private final PasswordEncoder encoder;

    List<Field> fieldList = Arrays.asList(
            new Field("int64", true, "account_id"),
            new Field("string", true, "username"),
            new Field("string", true, "password"),
            new Field("string", true, "email"),
            new Field("int32", true, "age"),
            new Field("int32", true, "gender"),
            new Field("string", true, "major"),
            new Field("int32", true, "grade")
    );
    Schema schema = Schema.builder()
            .type("struct")
            .fields(fieldList)
            .optional(false)
            .name("account")
            .build();

    public KafkaUserDto send(String topic, SignUpUserDto account) {

        UserPayload payload = UserPayload.builder()
                .account_id(account.getAccount_id())
                .username(account.getUsername())
                .password(encoder.encode(account.getPassword()))
                .email(account.getEmail())
                .age(account.getAge())
                .gender(1)
                .major(account.getMajor())
                .grade(account.getGrade())
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
