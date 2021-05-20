package com.example.jwt.dto;

import com.example.jwt.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    String username;
    String email;
    Integer age;

    List<Post> createdPost;
    List<Post> participated;
    List<Post> waiting;

}
