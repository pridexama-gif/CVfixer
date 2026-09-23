package com.cvfixer.matching;

import java.util.List;

public record JobMatchResponse(
        Long cvId,
        Long jobId,
        String jobTitle,
        int score,
        String recommendation,
        String candidateName,
        List<String> matchedSkills,
        List<String> missingSkills,
        Integer candidateExperienceYears,
        String candidateEmail
) {
}
