package com.example.jwt.entity.account;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class LogOutUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String username;
    Boolean isLogOut;
}
