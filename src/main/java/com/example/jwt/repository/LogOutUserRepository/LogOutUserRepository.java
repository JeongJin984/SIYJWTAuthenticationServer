package com.example.jwt.repository.LogOutUserRepository;

import com.example.jwt.entity.account.Account;
import com.example.jwt.entity.account.LogOutUser;
import com.example.jwt.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogOutUserRepository extends CommonRepository<LogOutUser, Long>, LogOutUserRepositoryCustom {
}