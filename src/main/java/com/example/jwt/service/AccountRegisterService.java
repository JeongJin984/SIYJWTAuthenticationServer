package com.example.jwt.service;

import com.example.jwt.converter.GenderAttributeConverter;
import com.example.jwt.dto.SignUpUserDto;
import com.example.jwt.entity.account.Account;
import com.example.jwt.entity.account.authorization.AccountRole;
import com.example.jwt.entity.account.authorization.Role;
import com.example.jwt.kafka.producer.AccountProducer;
import com.example.jwt.kafka.producer.AccountRoleProducer;
import com.example.jwt.repository.AccountRepository.AccountRepository;
import com.example.jwt.repository.AccountRoleRepository.AccountRoleRepository;
import com.example.jwt.repository.RoleRepository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountRegisterService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountProducer accountProducer;
    private final AccountRoleProducer accountRoleProducer;

    ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public Account registerNewAccount(Account account) {
        if (accountRepository.findAccountByUsername(account.getUsername()) != null) {
            throw new UsernameNotFoundException(
                    "There is an account with that email address:" + account.getUsername());
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));

        List<AccountRole> accountRoleList = new ArrayList<>();
        Role role = roleRepository.findRoleByRoleName("ROLE_USER");
        Account registeredAccount = accountRepository.save(account);
        accountRoleList.add(new AccountRole(registeredAccount, role));
        registeredAccount.setAccountRoles(accountRoleList);

        return registeredAccount;
    }

    //Do not use
    public void registerNewAccountByKafka(SignUpUserDto account) {

        accountProducer.send("account", account);

        List<Long> accountRole = new ArrayList<>();
        accountRole.add(account.getAccount_id());
        accountRole.add(roleRepository.findRoleByRoleName("ROLE_USER").getId());

        accountRoleProducer.send("account_role", accountRole);
    }

    public boolean isValidAccount(Account account) {
        return true;
    }

}
