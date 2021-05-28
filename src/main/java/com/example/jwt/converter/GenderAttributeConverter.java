package com.example.jwt.converter;

import com.example.jwt.entity.account.info.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderAttributeConverter implements AttributeConverter<Gender, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        if(gender.name().equals("MALE")) {
            return 1;
        } else if(gender.name().equals("FEMALE")) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public Gender convertToEntityAttribute(Integer code) {
        if(code == 1) {
            return Gender.MALE;
        } else if(code == -1) {
            return Gender.FEMALE;
        } else {
            return Gender.ALL;
        }
    }
}
