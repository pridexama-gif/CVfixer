package com.cvfixer.matching;

import java.util.List;

public record JobDescriptionRequest(
        String title,
        String description,
        String requiredSkills,
        Integer minExperienceYears
) {
}

record JobDescriptionResponse(
        Long id,
        String title,
        String description,
        String requiredSkills,
        Integer minExperienceYears
) {
    static JobDescriptionResponse from(JobDescription job) {
        return new JobDescriptionResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getRequiredSkills(),
                job.getMinExperienceYears()
        );
    }
}
