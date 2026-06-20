package com.priyanshu.portfolio.service;

import com.priyanshu.portfolio.dto.ProjectRequest;
import com.priyanshu.portfolio.dto.ProjectResponse;
import com.priyanshu.portfolio.dto.SkillResponse;
import com.priyanshu.portfolio.enums.ProjectStatus;
import com.priyanshu.portfolio.exception.ResourceNotFoundException;
import com.priyanshu.portfolio.model.Project;
import com.priyanshu.portfolio.model.Skill;
import com.priyanshu.portfolio.repository.ProjectRepository;
import com.priyanshu.portfolio.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .sorted((a, b) -> a.getDisplayOrder().compareTo(b.getDisplayOrder()))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectBySlug(String slug) {
        Project project = projectRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with slug: " + slug));
        return toResponse(project);
    }

    public ProjectResponse createProject(ProjectRequest request) {
        Project project = new Project();
        applyRequest(project, request);
        Project saved = projectRepository.save(project);
        return toResponse(saved);
    }

    public ProjectResponse updateProject(UUID id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        applyRequest(project, request);
        Project updated = projectRepository.save(project);
        return toResponse(updated);
    }

    public void deleteProject(UUID id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }

    private void applyRequest(Project project, ProjectRequest request) {
        project.setTitle(request.getTitle());
        project.setSlug(request.getSlug());
        project.setSummary(request.getSummary());
        project.setDescription(request.getDescription());
        project.setImageUrl(request.getImageUrl());
        project.setLiveUrl(request.getLiveUrl());
        project.setGithubUrl(request.getGithubUrl());
        project.setStatus(request.getStatus() != null ? request.getStatus() : ProjectStatus.IN_PROGRESS);
        project.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);

        if (request.getSkillIds() != null) {
            Set<Skill> skills = request.getSkillIds().stream()
                    .map(skillId -> skillRepository.findById(skillId)
                            .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + skillId)))
                    .collect(Collectors.toSet());
            project.setSkills(skills);
        }
    }

    private ProjectResponse toResponse(Project project) {
        Set<SkillResponse> skillResponses = project.getSkills().stream()
                .map(skill -> new SkillResponse(skill.getId(), skill.getName(), skill.getCategory(), skill.getProficiency()))
                .collect(Collectors.toSet());

        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getSlug(),
                project.getSummary(),
                project.getDescription(),
                project.getImageUrl(),
                project.getLiveUrl(),
                project.getGithubUrl(),
                project.getStatus(),
                project.getDisplayOrder(),
                project.getCreatedAt(),
                skillResponses
        );
    }
}