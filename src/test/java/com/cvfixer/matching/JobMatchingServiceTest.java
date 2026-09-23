package com.cvfixer.matching;

import com.cvfixer.cv.CvDocument;
import com.cvfixer.cv.CvDocumentRepository;
import com.cvfixer.profile.CandidateProfile;
import com.cvfixer.profile.CandidateProfileRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JobMatchingServiceTest {

    @Test
    void matchShouldReturnHighScoreWhenCandidateMatchesJobSkills() {
        JobDescriptionRepository jobRepository = Mockito.mock(JobDescriptionRepository.class);
        CandidateProfileRepository candidateRepository = Mockito.mock(CandidateProfileRepository.class);
        CandidateProfileServiceStub profileService = new CandidateProfileServiceStub();

        JobDescription job = new JobDescription(
                "Java Backend Developer",
                "We are looking for a Java engineer with Spring Boot, REST APIs, SQL, and Docker expertise.",
                "java, spring boot, rest, sql, docker",
                3
        );

        CandidateProfile candidateProfile = new CandidateProfile(
                10L,
                "John Smith",
                "john.smith@example.com",
                "+1 555 123 4567",
                "java, spring boot, rest, sql, docker, git",
                6,
                "Bachelor",
                "Experienced Java developer"
        );

        Mockito.when(jobRepository.findById(5L)).thenReturn(Optional.of(job));
        Mockito.when(candidateRepository.findByCvId(10L)).thenReturn(Optional.of(candidateProfile));

        JobMatchingService service = new JobMatchingService(jobRepository, candidateRepository, profileService);
        JobMatchResponse response = service.match(5L, 10L);

        assertTrue(response.score() >= 60);
        assertTrue(response.matchedSkills().contains("java"));
        assertTrue(response.matchedSkills().contains("spring boot"));
        assertTrue(response.missingSkills().isEmpty() || response.missingSkills().size() < 5);
    }

    private static class CandidateProfileServiceStub extends com.cvfixer.profile.CandidateProfileService {
        CandidateProfileServiceStub() {
            super(null, null);
        }
    }
}
