package com.example.jwt.api;

import com.example.jwt.dto.AccountContext;
import com.example.jwt.entity.account.Account;
import com.example.jwt.entity.account.AccountRole;
import com.example.jwt.entity.account.Role;
import com.example.jwt.repository.AccountRepository.AccountRepository;
import com.example.jwt.security.service.CustomTokenExtractor;
import com.example.jwt.security.util.jwt.GetTokenInfo;
import com.example.jwt.security.util.jwt.RefreshToken.RefreshTokenConstant;
import com.example.jwt.security.util.jwt.TokenUtils;
import com.example.jwt.security.util.jwt.accesToken.TokenConstant;
import com.example.jwt.service.AccountRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static com.example.jwt.security.util.jwt.GetTokenInfo.getUserName;

@RestController
@RequiredArgsConstructor
public class JwtApiController {

    private final CustomTokenExtractor tokenExtractor;
    private final AccountRegisterService accountRegisterService;
    private final AccountRepository accountRepository;

    @GetMapping(value = "/api/refresh")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        String accessToken = tokenExtractor.getTokenFromRequest(request, TokenConstant.AUTH_HEADER).substring(6);
        String refreshToken = tokenExtractor.getTokenFromRequest(request, RefreshTokenConstant.AUTH_HEADER).substring(6);

        if(GetTokenInfo.isValidToken(refreshToken)) {
            String username = getUserName(accessToken);
            final String new_token = TokenUtils.generateJwtToken(new AccountContext(username, null, null), 30);

            return new ResponseEntity<>(new_token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }

    @GetMapping(value = "/api/user")
    public ResponseEntity<Account> loadUser(HttpServletRequest request, HttpServletResponse response) {

        try {
            String accessToken = tokenExtractor.getTokenFromRequest(request, TokenConstant.AUTH_HEADER).substring(6);
            String refreshToken = tokenExtractor.getTokenFromRequest(request, RefreshTokenConstant.AUTH_HEADER).substring(6);

            if(GetTokenInfo.isValidToken(refreshToken)) {
                String username = getUserName(accessToken);
                Account account = accountRepository.findAccountByUsername(username);

                return new ResponseEntity<>(account, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }


    @PostMapping(value = "/api/signup")
    String signup(@RequestBody Account account) {
        try {
            accountRegisterService.registerNewAccount(account);
        } catch (Exception e) {
            if(e instanceof UsernameNotFoundException) {
                return e.getMessage();
            }
        }
        return "success";
    }
}
