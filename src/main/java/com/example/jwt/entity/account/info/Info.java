package com.example.jwt.entity.account.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Converter;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Info {

    @Enumerated(EnumType.STRING)
    Major major;

    Gender gender;

    Integer grade;
}
