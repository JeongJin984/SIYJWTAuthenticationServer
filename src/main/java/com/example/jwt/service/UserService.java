package com.example.jwt.service;

import com.example.jwt.dto.UserProfile;
import com.example.jwt.dto.post.RelatedPost;
import com.example.jwt.entity.account.Account;
import com.example.jwt.entity.post.Post;
import com.example.jwt.feign.PostServiceClient;
import com.example.jwt.repository.AccountRepository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {
//    private final AccountRepository accountRepository;
//    private final Environment env;
//    private final RestTemplate restTemplate;
//    private final PostServiceClient postServiceClient;
//
//    private final String post_service_url = Objects.requireNonNull(env.getProperty("post_service.url"));
//
//    public UserProfile getUserProfileByUsername(String username) {
//        Account account = accountRepository.findAccountByUsername(username);
//
//        if(account == null) throw new UsernameNotFoundException("User not found: " + username);
//
//        String postUrl = String.format(post_service_url, username, 10, 5);
//        ResponseEntity<List<RelatedPost>> relatedPostResponse =
//                restTemplate.exchange(
//                        postUrl,
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<List<RelatedPost>>() {}
//                );
//
//
//        List<Post> relatedPostList = postServiceClient.getAllRelatedPost(username);
//        UserProfile userProfile = new UserProfile();
//        userProfile.setUsername(username);
//        userProfile.setAge(account.getAge());
//        userProfile.setEmail(account.getEmail());
//
//        return userProfile;
//    }
}
