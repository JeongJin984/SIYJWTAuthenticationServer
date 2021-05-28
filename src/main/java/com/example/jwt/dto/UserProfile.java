package com.example.jwt.dto;

import com.example.jwt.dto.post.RelatedPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    String username;
    String email;
    Integer age;

    RelatedPost createdPost;
    RelatedPost participated;
    RelatedPost waiting;
}
