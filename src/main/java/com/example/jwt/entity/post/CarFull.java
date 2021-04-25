package com.example.jwt.entity.post;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@DiscriminatorValue("CarFull")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarFull extends Post{
    private String category;

    @Builder
    public CarFull(String title, String content, String category) {
        super(title, content);
        this.setCategory("study");
        this.category = category;
    }
    @Builder
    public CarFull(Post post, String category) {
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
