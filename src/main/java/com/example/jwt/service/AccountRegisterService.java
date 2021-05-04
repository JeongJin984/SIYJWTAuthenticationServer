package com.example.jwt.service;

import com.example.jwt.entity.account.Account;
import com.example.jwt.entity.account.AccountRole;
import com.example.jwt.entity.account.Role;
import com.example.jwt.repository.AccountRepository.AccountRepository;
import com.example.jwt.repository.AccountRoleRepository.AccountRoleRepository;
import com.example.jwt.repository.RoleRepository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final AccountRoleRepository accountRoleRepository;

    @Transactional
    public Account registerNewAccount(Account account) throws Exception {
        if (accountRepository.findAccountByUsername(account.getUsername()) != null) {
            throw new UsernameNotFoundException(
                    "There is an account with that email address:" + account.getUsername());
        }
        Account user = new Account(
                account.getUsername(),
                passwordEncoder.encode(account.getPassword()),
                account.getEmail(),
                account.getAge()
        );

        List<AccountRole> accountRoleList = new ArrayList<>();
        Role role = roleRepository.findRoleByRoleName("ROLE_USER");
        Account registeredAccount = accountRepository.save(user);
        accountRoleList.add(new AccountRole(registeredAccount, role));
        registeredAccount.setAccountRoles(accountRoleList);
        return registeredAccount;
    }

}
