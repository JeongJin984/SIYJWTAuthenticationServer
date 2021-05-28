package com.example.jwt.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatedPost {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class PostData {
        private Long id;
        private String category;
        private String title;
        private String writer;
        private String createdAt;
    }

    private int size;
    private int currentPage;
    private int maxPage;
    private List<PostData> data = new ArrayList<>();
}
