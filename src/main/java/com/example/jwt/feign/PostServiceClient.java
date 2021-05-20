package com.example.jwt.feign;

import com.example.jwt.dto.post.RelatedPost;
import com.example.jwt.entity.post.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="siy-post-service")
public interface PostServiceClient {

    @GetMapping("/post-service/related/{username}")
    public List<Post> getAllRelatedPost(@PathVariable("username") String username);
}
