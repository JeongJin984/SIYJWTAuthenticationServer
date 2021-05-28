package com.example.jwt.kafka.dto;

import com.example.jwt.kafka.dto.Field;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Schema {
    private String type;
    private List<Field> fields;
    private boolean optional;
    private String name;
}
