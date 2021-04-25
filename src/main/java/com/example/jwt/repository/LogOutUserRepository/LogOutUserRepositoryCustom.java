package com.example.jwt.repository.LogOutUserRepository;

import com.example.jwt.entity.account.Account;
import com.example.jwt.entity.account.LogOutUser;

public interface LogOutUserRepositoryCustom {
    public LogOutUser findByUsername(String username);
}
