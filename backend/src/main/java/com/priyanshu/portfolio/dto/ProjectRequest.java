package com.priyanshu.portfolio.dto;

import com.priyanshu.portfolio.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ProjectRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Slug is required")
    private String slug;

    private String summary;
    private String description;
    private String imageUrl;
    private String liveUrl;
    private String githubUrl;
    private ProjectStatus status;
    private Integer displayOrder;
    private Set<UUID> skillIds;
}