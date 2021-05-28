package com.example.jwt.api;

import com.example.jwt.dto.AccountContext;
import com.example.jwt.entity.account.Account;
import com.example.jwt.kafka.producer.AccountProducer;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class JwtApiController {

    private final CustomTokenExtractor tokenExtractor;
    private final TokenUtils tokenUtils;
    private final GetTokenInfo getTokenInfo;

    @GetMapping(value = "/refresh")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        String accessToken = tokenExtractor.getTokenFromRequest(request, TokenConstant.AUTH_HEADER).substring(6);
        String refreshToken = tokenExtractor.getTokenFromRequest(request, RefreshTokenConstant.AUTH_HEADER).substring(6);

        if(getTokenInfo.isValidToken(refreshToken)) {
            String username = getTokenInfo.getUserName(accessToken);
            final String new_token = tokenUtils.generateJwtToken(new AccountContext(username, null, null), 30);

            return new ResponseEntity<>(new_token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }
}
