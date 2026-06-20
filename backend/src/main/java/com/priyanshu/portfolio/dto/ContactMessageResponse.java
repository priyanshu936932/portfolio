package com.priyanshu.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ContactMessageResponse {
    private UUID id;
    private String name;
    private String email;
    private String subject;
    private String message;
    private Boolean read;
    private LocalDateTime createdAt;
}