package com.example.jwt.service;

import com.example.jwt.dto.UserProfile;
import com.example.jwt.dto.post.RelatedPost;
import com.example.jwt.entity.account.Account;
import com.example.jwt.feign.PostServiceClient;
import com.example.jwt.repository.AccountRepository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final AccountRepository accountRepository;
    private final PostServiceClient postServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public UserProfile getUserProfileByUsername(String username) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        Account account = accountRepository.findAccountByUsername(username);

        if(account == null) throw new UsernameNotFoundException("User not found: " + username);

        log.info("Before call post-service for createdPosts");
        RelatedPost createdPost = circuitBreaker.run(() -> postServiceClient.getCreatedPost(username)
                , throwable -> new RelatedPost());
        log.info("After call post-service for createdPosts");

        log.info("Before call post-service for participatedPosts");
        RelatedPost participatedPost = circuitBreaker.run(() -> postServiceClient.getParticipatedPost(username)
                , throwable -> new RelatedPost());
        log.info("After call post-service for participatedPosts");

        log.info("Before call post-service for waitingPosts");
        RelatedPost waitingPost = circuitBreaker.run(() -> postServiceClient.getWaitingPost(username)
                , throwable -> new RelatedPost());
        log.info("After call post-service for waitingPosts");


        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(username);
        userProfile.setAge(account.getAge());
        userProfile.setEmail(account.getEmail());
        userProfile.setCreatedPost(createdPost);
        userProfile.setParticipated(participatedPost);
        userProfile.setWaiting(waitingPost);

        return userProfile;
    }
}
