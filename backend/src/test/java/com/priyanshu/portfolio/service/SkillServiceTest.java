package com.priyanshu.portfolio.service;

import com.priyanshu.portfolio.dto.SkillRequest;
import com.priyanshu.portfolio.exception.ResourceNotFoundException;
import com.priyanshu.portfolio.model.Skill;
import com.priyanshu.portfolio.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock SkillRepository skillRepository;
    @InjectMocks SkillService skillService;

    // --- proficiency default on create ---

    @Test
    void createSkill_defaultsProficiencyToThreeWhenNull() {
        SkillRequest request = buildRequest("Java", "Backend", null);
        when(skillRepository.save(any(Skill.class))).thenAnswer(inv -> inv.getArgument(0));

        skillService.createSkill(request);

        ArgumentCaptor<Skill> captor = ArgumentCaptor.forClass(Skill.class);
        verify(skillRepository).save(captor.capture());
        assertThat(captor.getValue().getProficiency()).isEqualTo(3);
    }

    @Test
    void createSkill_usesProficiencyFromRequestWhenProvided() {
        SkillRequest request = buildRequest("Java", "Backend", 5);
        when(skillRepository.save(any(Skill.class))).thenAnswer(inv -> inv.getArgument(0));

        skillService.createSkill(request);

        ArgumentCaptor<Skill> captor = ArgumentCaptor.forClass(Skill.class);
        verify(skillRepository).save(captor.capture());
        assertThat(captor.getValue().getProficiency()).isEqualTo(5);
    }

    // --- proficiency handling on update ---

    @Test
    void updateSkill_keepsExistingProficiencyWhenNullInRequest() {
        UUID id = UUID.randomUUID();
        Skill existing = makeSkill(id, "Java", "Backend", 4);

        SkillRequest request = buildRequest("Java Updated", "Backend", null);

        when(skillRepository.findById(id)).thenReturn(Optional.of(existing));
        when(skillRepository.save(any(Skill.class))).thenAnswer(inv -> inv.getArgument(0));

        skillService.updateSkill(id, request);

        ArgumentCaptor<Skill> captor = ArgumentCaptor.forClass(Skill.class);
        verify(skillRepository).save(captor.capture());
        assertThat(captor.getValue().getProficiency()).isEqualTo(4);
    }

    @Test
    void updateSkill_overwritesProficiencyWhenProvided() {
        UUID id = UUID.randomUUID();
        Skill existing = makeSkill(id, "Java", "Backend", 2);

        SkillRequest request = buildRequest("Java", "Backend", 5);

        when(skillRepository.findById(id)).thenReturn(Optional.of(existing));
        when(skillRepository.save(any(Skill.class))).thenAnswer(inv -> inv.getArgument(0));

        skillService.updateSkill(id, request);

        ArgumentCaptor<Skill> captor = ArgumentCaptor.forClass(Skill.class);
        verify(skillRepository).save(captor.capture());
        assertThat(captor.getValue().getProficiency()).isEqualTo(5);
    }

    // --- error paths ---

    @Test
    void updateSkill_throwsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(skillRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> skillService.updateSkill(id, buildRequest("X", "Y", null)))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteSkill_throwsAndNeverCallsDeleteWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(skillRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> skillService.deleteSkill(id))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(skillRepository, never()).deleteById(any());
    }

    // --- helpers ---

    private SkillRequest buildRequest(String name, String category, Integer proficiency) {
        SkillRequest r = new SkillRequest();
        r.setName(name);
        r.setCategory(category);
        r.setProficiency(proficiency);
        return r;
    }

    private Skill makeSkill(UUID id, String name, String category, int proficiency) {
        Skill s = new Skill();
        s.setId(id);
        s.setName(name);
        s.setCategory(category);
        s.setProficiency(proficiency);
        s.setCreatedAt(LocalDateTime.now());
        return s;
    }
}
