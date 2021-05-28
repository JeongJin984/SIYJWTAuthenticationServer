package com.example.jwt.api;

import com.example.jwt.dto.SignUpUserDto;
import com.example.jwt.dto.UserProfile;
import com.example.jwt.dto.UserResponse;
import com.example.jwt.entity.account.Account;
import com.example.jwt.repository.AccountRepository.AccountRepository;
import com.example.jwt.security.service.CustomTokenExtractor;
import com.example.jwt.security.util.jwt.GetTokenInfo;
import com.example.jwt.security.util.jwt.RefreshToken.RefreshTokenConstant;
import com.example.jwt.security.util.jwt.accesToken.TokenConstant;
import com.example.jwt.service.AccountRegisterService;
import com.example.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final AccountRepository accountRepository;
    private final CustomTokenExtractor tokenExtractor;
    private final GetTokenInfo getTokenInfo;
    private final AccountRegisterService accountRegisterService;

    @GetMapping("/profile/{username}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable("username") String username) {
        UserProfile userProfile = userService.getUserProfileByUsername(username);

        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<UserResponse> loadUser(HttpServletRequest request, HttpServletResponse response) {

        try {
            String accessToken = tokenExtractor.getTokenFromRequest(request, TokenConstant.AUTH_HEADER).substring(6);
            String refreshToken = tokenExtractor.getTokenFromRequest(request, RefreshTokenConstant.AUTH_HEADER).substring(6);

            if(getTokenInfo.isValidToken(refreshToken)) {
                String username = getTokenInfo.getUserName(accessToken);
                Account account = accountRepository.findAccountByUsername(username);
                UserResponse userResponse = new UserResponse(
                        account.getUsername(),
                        account.getEmail(),
                        account.getAge(),
                        account.getQualification().getMajor().name(),
                        account.getQualification().getGender().name(),
                        account.getQualification().getGrade()
                );

                return new ResponseEntity<>(userResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
    }

    @PostMapping(value = "/signup")
    String signup(@RequestBody SignUpUserDto signUpData) {
        try {
            Account account = new Account(
                    signUpData.getUsername(),
                    signUpData.getPassword(),
                    signUpData.getEmail(),
                    signUpData.getAge(),
                    signUpData.getGender(),
                    signUpData.getMajor(),
                    signUpData.getGrade()
            );
            Long account_id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
            account.setId(account_id);
            signUpData.setAccount_id(account_id);

            //accountRegisterService.registerNewAccount(account);
            accountRegisterService.registerNewAccountByKafka(signUpData);
        } catch (Exception e) {
            if(e instanceof UsernameNotFoundException) {
                return e.getMessage();
            }
        }
        return "success";
    }
}
