package com.cvfixer.profile;

import java.util.Arrays;
import java.util.List;

public record CandidateProfileResponse(
        Long cvId,
        String fullName,
        String email,
        String phone,
        List<String> skills,
        Integer experienceYears,
        String education,
        String summary
) {
    public static CandidateProfileResponse from(CandidateProfile profile) {
        List<String> skills = profile.getSkills() == null || profile.getSkills().isBlank()
                ? List.of()
                : Arrays.stream(profile.getSkills().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isBlank())
                        .toList();

        return new CandidateProfileResponse(
                profile.getCvId(),
                profile.getFullName(),
                profile.getEmail(),
                profile.getPhone(),
                skills,
                profile.getExperienceYears(),
                profile.getEducation(),
                profile.getSummary()
        );
    }
}
