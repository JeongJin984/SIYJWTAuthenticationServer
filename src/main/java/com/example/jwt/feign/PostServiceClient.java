package com.example.jwt.feign;

import com.example.jwt.dto.post.RelatedPost;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="siy-post-service")
public interface PostServiceClient {
    @GetMapping("/api/post/my")
    RelatedPost getCreatedPost(@RequestParam(name = "username") String username);

    @GetMapping("/api/post/application")
    RelatedPost getParticipatedPost(@RequestParam(name = "username") String username);

    @GetMapping("/api/post/participants")
    RelatedPost getWaitingPost(@RequestParam(name = "username") String username);
}
