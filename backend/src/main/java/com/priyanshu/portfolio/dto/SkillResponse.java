package com.priyanshu.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class SkillResponse {
    private UUID id;
    private String name;
    private String category;
    private Integer proficiency;
}