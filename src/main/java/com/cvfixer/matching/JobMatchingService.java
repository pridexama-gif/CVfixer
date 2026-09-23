package com.cvfixer.matching;

import com.cvfixer.profile.CandidateProfile;
import com.cvfixer.profile.CandidateProfileRepository;
import com.cvfixer.profile.CandidateProfileResponse;
import com.cvfixer.profile.CandidateProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@Transactional
public class JobMatchingService {

    private static final List<String> SKILL_LIBRARY = List.of(
            "java", "spring boot", "rest", "api", "sql", "hibernate", "jpa",
            "docker", "aws", "git", "microservices", "agile", "kubernetes",
            "javascript", "react", "node", "python", "postgresql", "mongodb"
    );

    private final JobDescriptionRepository jobDescriptionRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final CandidateProfileService candidateProfileService;

    public JobMatchingService(JobDescriptionRepository jobDescriptionRepository,
                             CandidateProfileRepository candidateProfileRepository,
                             CandidateProfileService candidateProfileService) {
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.candidateProfileService = candidateProfileService;
    }

    public JobMatchResponse match(Long jobId, Long cvId) {
        JobDescription job = jobDescriptionRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job description not found with id: " + jobId));

        CandidateProfileResponse candidate = candidateProfileRepository.findByCvId(cvId)
                .map(CandidateProfileResponse::from)
                .orElseGet(() -> candidateProfileService.extractProfile(cvId));

        Set<String> jobSkills = extractSkills(job.getTitle() + " " + job.getDescription() + " " + job.getRequiredSkills());
        Set<String> candidateSkills = new LinkedHashSet<>(candidate.skills());

        List<String> matchedSkills = SKILL_LIBRARY.stream()
                .filter(jobSkills::contains)
                .filter(candidateSkills::contains)
                .toList();

        List<String> missingSkills = SKILL_LIBRARY.stream()
                .filter(jobSkills::contains)
                .filter(skill -> !candidateSkills.contains(skill))
                .toList();

        int baseScore = jobSkills.isEmpty() ? 0 : (matchedSkills.size() * 100) / jobSkills.size();
        int experiencePenalty = candidate.experienceYears() != null && job.getMinExperienceYears() != null && candidate.experienceYears() < job.getMinExperienceYears() ? 15 : 0;
        int finalScore = Math.max(0, Math.min(100, baseScore - experiencePenalty));

        String recommendation = finalScore >= 80 ? "Strong match" : finalScore >= 60 ? "Good match" : finalScore >= 40 ? "Possible match" : "Low match";

        return new JobMatchResponse(
                cvId,
                jobId,
                job.getTitle(),
                finalScore,
                recommendation,
                candidate.fullName(),
                matchedSkills,
                missingSkills,
                candidate.experienceYears(),
                candidate.email()
        );
    }

    private Set<String> extractSkills(String rawText) {
        if (rawText == null || rawText.isBlank()) {
            return Set.of();
        }

        String normalized = rawText.toLowerCase(Locale.ROOT);
        Set<String> result = new LinkedHashSet<>();
        for (String skill : SKILL_LIBRARY) {
            if (normalized.contains(skill.toLowerCase(Locale.ROOT))) {
                result.add(skill);
            }
        }
        return result;
    }
}
