package com.example.jwt.repository.AccountRepository;

import com.example.jwt.entity.account.Account;
import com.example.jwt.entity.account.LogOutUser;

import java.util.List;

public interface AccountRepositoryCustom {
    public Account findByUsername(String username);
}
