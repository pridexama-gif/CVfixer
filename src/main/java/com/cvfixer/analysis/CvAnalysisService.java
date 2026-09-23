package com.cvfixer.analysis;

import com.cvfixer.cv.CvDocument;
import com.cvfixer.cv.CvDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CvAnalysisService {

    private static final List<String> DEFAULT_SKILLS = List.of(
            "java", "spring boot", "rest", "api", "sql", "hibernate", "jpa",
            "docker", "aws", "git", "microservices", "agile", "kubernetes"
    );

    private final CvDocumentRepository repository;

    public CvAnalysisService(CvDocumentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public CvAnalysisResult analyze(Long cvId) {
        CvDocument cvDocument = repository.findById(cvId)
                .orElseThrow(() -> new IllegalArgumentException("CV not found with id: " + cvId));

        return analyze(cvDocument);
    }

    public CvAnalysisResult analyze(CvDocument cvDocument) {
        String text = cvDocument.getContent() == null ? "" : cvDocument.getContent();
        String normalized = text.toLowerCase(Locale.ROOT);

        Set<String> matchedSkills = DEFAULT_SKILLS.stream()
                .filter(skill -> normalized.contains(skill.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toSet());

        List<String> matchedSkillsOrdered = DEFAULT_SKILLS.stream()
                .filter(matchedSkills::contains)
                .toList();

        List<String> missingSkills = DEFAULT_SKILLS.stream()
                .filter(skill -> !matchedSkills.contains(skill))
                .toList();

        int score = DEFAULT_SKILLS.isEmpty() ? 0 : (matchedSkills.size() * 100) / DEFAULT_SKILLS.size();

        return new CvAnalysisResult(
                cvDocument.getId(),
                cvDocument.getFileName(),
                matchedSkillsOrdered,
                missingSkills,
                score
        );
    }
}
