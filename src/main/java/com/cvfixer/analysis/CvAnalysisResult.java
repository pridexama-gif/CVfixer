package com.cvfixer.analysis;

import java.util.List;

public record CvAnalysisResult(
        Long cvId,
        String fileName,
        List<String> matchedSkills,
        List<String> missingSkills,
        int matchScore
) {
}
