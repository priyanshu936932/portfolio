package com.priyanshu.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TagResponse {
    private UUID id;
    private String name;
}