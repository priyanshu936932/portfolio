package com.priyanshu.portfolio.dto;

import com.priyanshu.portfolio.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ProjectResponse {
    private UUID id;
    private String title;
    private String slug;
    private String summary;
    private String description;
    private String imageUrl;
    private String liveUrl;
    private String githubUrl;
    private ProjectStatus status;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private Set<SkillResponse> skills;
}