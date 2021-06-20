package com.example.jwt.repository.AccountRepository;

import com.example.jwt.entity.account.Account;
import com.example.jwt.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CommonRepository<Account, Long>, AccountRepositoryCustom {
    Account findAccountByUsername(String username); // AccountRole is Empty, Password embedded
    Account deleteAccountByUsername(String username);
}