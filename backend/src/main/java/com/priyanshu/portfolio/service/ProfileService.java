package com.priyanshu.portfolio.service;

import com.priyanshu.portfolio.dto.ProfileRequest;
import com.priyanshu.portfolio.dto.ProfileResponse;
import com.priyanshu.portfolio.exception.ResourceNotFoundException;
import com.priyanshu.portfolio.model.Profile;
import com.priyanshu.portfolio.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileResponse getProfile() {
        Profile profile = profileRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Profile not configured yet"));
        return toResponse(profile);
    }

    public ProfileResponse updateProfile(ProfileRequest request) {
        Profile profile = profileRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Profile not configured yet"));

        profile.setFullName(request.getFullName());
        profile.setEmail(request.getEmail());
        profile.setPhone(request.getPhone());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setBio(request.getBio());
        profile.setUpdatedAt(LocalDateTime.now());

        Profile updated = profileRepository.save(profile);
        return toResponse(updated);
    }

    private ProfileResponse toResponse(Profile profile) {
        return new ProfileResponse(
                profile.getFullName(),
                profile.getEmail(),
                profile.getPhone(),
                profile.getLinkedinUrl(),
                profile.getGithubUrl(),
                profile.getBio()
        );
    }
}