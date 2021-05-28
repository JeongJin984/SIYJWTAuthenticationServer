package com.example.jwt.repository.AccountRepository;

import com.example.jwt.entity.account.Account;

public interface AccountRepositoryCustom {
    public Account findByUsername(String username);
}
