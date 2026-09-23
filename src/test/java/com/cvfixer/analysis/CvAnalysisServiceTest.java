package com.cvfixer.analysis;

import com.cvfixer.cv.CvDocument;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CvAnalysisServiceTest {

    private final CvAnalysisService service = new CvAnalysisService(null);

    @Test
    void analyzeShouldDetectMatchedSkills() {
        CvDocument document = new CvDocument("resume.txt",
                "Java developer with Spring Boot, REST API, SQL, Hibernate, Docker, Git experience");

        CvAnalysisResult result = service.analyze(document);

        assertEquals("resume.txt", result.fileName());
        assertTrue(result.matchedSkills().contains("java"));
        assertTrue(result.matchedSkills().contains("spring boot"));
        assertTrue(result.matchedSkills().contains("rest"));
        assertTrue(result.matchedSkills().contains("sql"));
        assertTrue(result.matchedSkills().contains("hibernate"));
        assertTrue(result.matchedSkills().contains("docker"));
        assertTrue(result.matchedSkills().contains("git"));
        assertTrue(result.matchScore() >= 50);
    }

    @Test
    void analyzeShouldMarkMissingSkillsWhenNotPresent() {
        CvDocument document = new CvDocument("resume.txt", "Java developer with SQL and Git");

        CvAnalysisResult result = service.analyze(document);

        assertTrue(result.missingSkills().contains("spring boot"));
        assertTrue(result.missingSkills().contains("docker"));
        assertTrue(result.matchScore() < 100);
    }
}
