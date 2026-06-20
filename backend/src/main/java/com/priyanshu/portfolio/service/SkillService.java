package com.priyanshu.portfolio.service;

import com.priyanshu.portfolio.dto.SkillRequest;
import com.priyanshu.portfolio.dto.SkillResponse;
import com.priyanshu.portfolio.exception.ResourceNotFoundException;
import com.priyanshu.portfolio.model.Skill;
import com.priyanshu.portfolio.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SkillResponse createSkill(SkillRequest request) {
        Skill skill = new Skill();
        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        skill.setProficiency(request.getProficiency() != null ? request.getProficiency() : 3);

        Skill saved = skillRepository.save(skill);
        return toResponse(saved);
    }

    public SkillResponse updateSkill(UUID id, SkillRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));

        skill.setName(request.getName());
        skill.setCategory(request.getCategory());
        if (request.getProficiency() != null) {
            skill.setProficiency(request.getProficiency());
        }

        Skill updated = skillRepository.save(skill);
        return toResponse(updated);
    }

    public void deleteSkill(UUID id) {
        if (!skillRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skill not found with id: " + id);
        }
        skillRepository.deleteById(id);
    }

    private SkillResponse toResponse(Skill skill) {
        return new SkillResponse(
                skill.getId(),
                skill.getName(),
                skill.getCategory(),
                skill.getProficiency()
        );
    }
}