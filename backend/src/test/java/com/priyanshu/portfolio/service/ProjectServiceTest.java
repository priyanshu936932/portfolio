package com.priyanshu.portfolio.service;

import com.priyanshu.portfolio.dto.ProjectRequest;
import com.priyanshu.portfolio.dto.ProjectResponse;
import com.priyanshu.portfolio.enums.ProjectStatus;
import com.priyanshu.portfolio.exception.ResourceNotFoundException;
import com.priyanshu.portfolio.model.Project;
import com.priyanshu.portfolio.model.Skill;
import com.priyanshu.portfolio.repository.ProjectRepository;
import com.priyanshu.portfolio.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock ProjectRepository projectRepository;
    @Mock SkillRepository skillRepository;
    @InjectMocks ProjectService projectService;

    // --- defaults ---

    @Test
    void createProject_defaultsStatusToInProgressWhenNull() {
        ProjectRequest request = buildRequest("My Project", "my-project");
        request.setStatus(null);

        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        projectService.createProject(request);

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(ProjectStatus.IN_PROGRESS);
    }

    @Test
    void createProject_usesProvidedStatusWhenGiven() {
        ProjectRequest request = buildRequest("Done Project", "done-project");
        request.setStatus(ProjectStatus.COMPLETED);

        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        projectService.createProject(request);

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(ProjectStatus.COMPLETED);
    }

    @Test
    void createProject_defaultsDisplayOrderToZeroWhenNull() {
        ProjectRequest request = buildRequest("My Project", "my-project");
        request.setDisplayOrder(null);

        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        projectService.createProject(request);

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(captor.capture());
        assertThat(captor.getValue().getDisplayOrder()).isZero();
    }

    // --- sorting ---

    @Test
    void getAllProjects_returnsSortedByDisplayOrderAscending() {
        Project p1 = makeProject("second", 2);
        Project p2 = makeProject("first",  1);
        Project p3 = makeProject("third",  3);

        when(projectRepository.findAll()).thenReturn(List.of(p1, p2, p3));

        List<ProjectResponse> result = projectService.getAllProjects();

        assertThat(result).extracting(ProjectResponse::getDisplayOrder)
                .containsExactly(1, 2, 3);
    }

    // --- skill many-to-many linking ---

    @Test
    void createProject_withSkillIds_linksSkillsCorrectly() {
        UUID skillId1 = UUID.randomUUID();
        UUID skillId2 = UUID.randomUUID();

        Skill s1 = new Skill(); s1.setId(skillId1); s1.setName("Java"); s1.setCategory("Backend"); s1.setProficiency(5);
        Skill s2 = new Skill(); s2.setId(skillId2); s2.setName("Vue");  s2.setCategory("Frontend"); s2.setProficiency(4);

        ProjectRequest request = buildRequest("Linked Project", "linked-project");
        request.setSkillIds(Set.of(skillId1, skillId2));

        when(skillRepository.findById(skillId1)).thenReturn(Optional.of(s1));
        when(skillRepository.findById(skillId2)).thenReturn(Optional.of(s2));
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        projectService.createProject(request);

        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(captor.capture());
        assertThat(captor.getValue().getSkills()).hasSize(2);
        assertThat(captor.getValue().getSkills()).extracting(Skill::getName)
                .containsExactlyInAnyOrder("Java", "Vue");
    }

    @Test
    void createProject_withUnknownSkillId_throwsResourceNotFoundException() {
        UUID badId = UUID.randomUUID();
        ProjectRequest request = buildRequest("Bad Skill Project", "bad-skill-project");
        request.setSkillIds(Set.of(badId));

        when(skillRepository.findById(badId)).thenReturn(Optional.empty());
        when(projectRepository.save(any(Project.class))).thenAnswer(inv -> inv.getArgument(0));

        assertThatThrownBy(() -> projectService.createProject(request))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    // --- error paths ---

    @Test
    void getProjectBySlug_throwsWhenNotFound() {
        when(projectRepository.findBySlug("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.getProjectBySlug("missing"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("missing");
    }

    @Test
    void deleteProject_throwsAndNeverCallsDeleteWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(projectRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> projectService.deleteProject(id))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(projectRepository, never()).deleteById(any());
    }

    // --- helpers ---

    private ProjectRequest buildRequest(String title, String slug) {
        ProjectRequest r = new ProjectRequest();
        r.setTitle(title);
        r.setSlug(slug);
        return r;
    }

    private Project makeProject(String slug, int order) {
        Project p = new Project();
        p.setId(UUID.randomUUID());
        p.setTitle(slug);
        p.setSlug(slug);
        p.setStatus(ProjectStatus.IN_PROGRESS);
        p.setDisplayOrder(order);
        p.setCreatedAt(LocalDateTime.now());
        p.setSkills(new HashSet<>());
        return p;
    }
}
