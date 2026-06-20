package com.priyanshu.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import java.util.Set;
import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class BlogPostRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Slug is required")
    private String slug;

    private String summary;

    @NotBlank(message = "Content is required")
    private String content;

    private String coverImageUrl;
    private Boolean published;
    private Set<UUID> tagIds;
}