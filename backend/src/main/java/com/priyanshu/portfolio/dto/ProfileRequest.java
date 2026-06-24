package com.priyanshu.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {
    @NotBlank
    private String fullName;

    @NotBlank
    private String email;

    private String phone;
    private String linkedinUrl;
    private String githubUrl;
    private String bio;
    private String resumeUrl;
}