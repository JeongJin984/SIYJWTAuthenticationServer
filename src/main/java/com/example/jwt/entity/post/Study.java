package com.example.jwt.entity.post;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@DiscriminatorValue("Study")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study extends Post{
    private String category;

    @Builder
    public Study(String title, String content, String category) {
        super(title, content);
        this.setCategory("study");
        this.category = category;
    }
    @Builder
    public Study(Post post, String category) {
        super(post.getTitle(),
                post.getContent(),
                post.getWriter(),
                post.getCurrentNumber(),
                post.getMaxNumber(),
                post.getDeadLine());
        this.setCategory("study");
        this.category = category;
    }
}
