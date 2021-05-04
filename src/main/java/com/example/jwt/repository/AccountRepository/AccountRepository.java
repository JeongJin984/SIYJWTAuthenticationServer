package com.example.jwt.repository.AccountRepository;

import com.example.jwt.entity.account.Account;
import com.example.jwt.entity.account.Role;
import com.example.jwt.repository.CommonRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CommonRepository<Account, Long>, AccountRepositoryCustom {
    Account findAccountByUsername(String username);
}