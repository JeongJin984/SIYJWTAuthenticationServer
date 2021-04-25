package com.example.jwt.security.service;

import com.example.jwt.dto.AccountContext;
import com.example.jwt.entity.account.Account;
import com.example.jwt.repository.AccountRepository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(s);

        if(account == null) {
            throw new UsernameNotFoundException("No user found with username: " + s);
        }

        Set<GrantedAuthority> userRoles = account.getAccountRoles()
                .stream()
                .map(accountRole -> new SimpleGrantedAuthority(accountRole.getRole().getRoleName()))
                .collect(Collectors.toSet());

        return new AccountContext(account, userRoles);
    }
}
