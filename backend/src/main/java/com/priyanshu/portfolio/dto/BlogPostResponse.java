package com.priyanshu.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class BlogPostResponse {
    private UUID id;
    private String title;
    private String slug;
    private String summary;
    private String content;
    private String coverImageUrl;
    private Boolean published;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private Set<TagResponse> tags;
}