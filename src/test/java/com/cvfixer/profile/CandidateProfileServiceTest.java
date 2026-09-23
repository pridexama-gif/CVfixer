package com.cvfixer.profile;

import com.cvfixer.cv.CvDocument;
import com.cvfixer.cv.CvDocumentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CandidateProfileServiceTest {

    @Test
    void extractProfileShouldExtractNameEmailSkillsAndExperience() {
        CvDocumentRepository cvRepository = Mockito.mock(CvDocumentRepository.class);
        CandidateProfileRepository profileRepository = Mockito.mock(CandidateProfileRepository.class);

        CvDocument cvDocument = new CvDocument("john.pdf", "John Smith\n" +
                "john.smith@example.com\n" +
                "+1 555 123 4567\n" +
                "Java, Spring Boot, REST API, SQL, Docker\n" +
                "6 years of experience in backend development\n" +
                "Bachelor of Science in Computer Science");
        cvDocument = new CvDocument("john.pdf", "John Smith\n" +
                "john.smith@example.com\n" +
                "+1 555 123 4567\n" +
                "Java, Spring Boot, REST API, SQL, Docker\n" +
                "6 years of experience in backend development\n" +
                "Bachelor of Science in Computer Science");

        Mockito.when(cvRepository.findById(1L)).thenReturn(Optional.of(cvDocument));
        Mockito.when(profileRepository.findByCvId(1L)).thenReturn(Optional.empty());
        Mockito.when(profileRepository.save(Mockito.any(CandidateProfile.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CandidateProfileService service = new CandidateProfileService(profileRepository, cvRepository);
        CandidateProfileResponse response = service.extractProfile(1L);

        assertEquals("John Smith", response.fullName());
        assertEquals("john.smith@example.com", response.email());
        assertTrue(response.skills().contains("java"));
        assertTrue(response.skills().contains("spring boot"));
        assertTrue(response.experienceYears() >= 6);
        assertEquals("bachelor", response.education().toLowerCase());
    }
}
