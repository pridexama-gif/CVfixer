package com.cvfixer.profile;

import com.cvfixer.cv.CvDocument;
import com.cvfixer.cv.CvDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class CandidateProfileService {

    private static final List<String> SKILL_LIBRARY = List.of(
            "java", "spring boot", "rest", "api", "sql", "hibernate", "jpa",
            "docker", "aws", "git", "microservices", "agile", "kubernetes",
            "javascript", "react", "node", "python", "postgresql", "mongodb"
    );

    private static final Pattern EMAIL_PATTERN = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}", Pattern.CASE_INSENSITIVE);
    private static final Pattern PHONE_PATTERN = Pattern.compile("(?:\\+?\\d[\\d() .-]{7,}\\d)");
    private static final Pattern EXPERIENCE_PATTERN = Pattern.compile("(?i)(\\d+)\\s*(?:\\+)?\\s*(?:years?|yrs?)");
    private static final Pattern EDUCATION_PATTERN = Pattern.compile("(?i)(bachelor|master|degree|diploma|university|college|b\.sc|m\.sc|mba)");

    private final CandidateProfileRepository profileRepository;
    private final CvDocumentRepository cvDocumentRepository;

    public CandidateProfileService(CandidateProfileRepository profileRepository,
                                  CvDocumentRepository cvDocumentRepository) {
        this.profileRepository = profileRepository;
        this.cvDocumentRepository = cvDocumentRepository;
    }

    public CandidateProfileResponse extractProfile(Long cvId) {
        CvDocument document = cvDocumentRepository.findById(cvId)
                .orElseThrow(() -> new IllegalArgumentException("CV not found with id: " + cvId));

        String text = Optional.ofNullable(document.getContent()).orElse("");
        String normalized = text.trim();

        String email = extractEmail(normalized);
        String phone = extractPhone(normalized);
        String fullName = extractName(normalized, email);
        List<String> skills = extractSkills(normalized);
        Integer experienceYears = extractExperienceYears(normalized);
        String education = extractEducation(normalized);
        String summary = createSummary(fullName, skills, experienceYears, education);

        CandidateProfile profile = profileRepository.findByCvId(cvId)
                .map(existing -> updateProfile(existing, cvId, fullName, email, phone, skills, experienceYears, education, summary))
                .orElseGet(() -> new CandidateProfile(cvId, fullName, email, phone, String.join(", ", skills), experienceYears, education, summary));

        return CandidateProfileResponse.from(profileRepository.save(profile));
    }

    private CandidateProfile updateProfile(CandidateProfile existing, Long cvId, String fullName, String email, String phone,
                                          List<String> skills, Integer experienceYears, String education, String summary) {
        existing = new CandidateProfile(cvId, fullName, email, phone,
                String.join(", ", skills), experienceYears, education, summary);
        return existing;
    }

    private String extractEmail(String text) {
        Matcher matcher = EMAIL_PATTERN.matcher(text);
        return matcher.find() ? matcher.group() : null;
    }

    private String extractPhone(String text) {
        Matcher matcher = PHONE_PATTERN.matcher(text);
        return matcher.find() ? matcher.group().replaceAll("\\s+", " ").trim() : null;
    }

    private String extractName(String text, String email) {
        List<String> lines = Arrays.stream(text.split("\\R"))
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .toList();

        for (String line : lines) {
            String candidate = line.replaceAll("\\s+", " ").trim();
            if (candidate.isEmpty()) {
                continue;
            }
            if (candidate.contains("@") || email != null && candidate.contains(email)) {
                continue;
            }
            if (candidate.matches(".*\\d.*")) {
                continue;
            }
            if (candidate.length() <= 80 && candidate.split(" ").length >= 2) {
                return candidate;
            }
        }

        return "Unknown Candidate";
    }

    private List<String> extractSkills(String text) {
        String lowerText = text.toLowerCase(Locale.ROOT);
        Set<String> found = new LinkedHashSet<>();

        for (String skill : SKILL_LIBRARY) {
            if (lowerText.contains(skill.toLowerCase(Locale.ROOT))) {
                found.add(skill);
            }
        }

        return found.stream().sorted(Comparator.comparingInt(skill -> SKILL_LIBRARY.indexOf(skill))).toList();
    }

    private Integer extractExperienceYears(String text) {
        Matcher matcher = EXPERIENCE_PATTERN.matcher(text);
        if (!matcher.find()) {
            return 0;
        }

        try {
            return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String extractEducation(String text) {
        Matcher matcher = EDUCATION_PATTERN.matcher(text);
        return matcher.find() ? matcher.group(1) : "Not specified";
    }

    private String createSummary(String fullName, List<String> skills, Integer experienceYears, String education) {
        return fullName + " has " + experienceYears + " years of experience and skills in "
                + (skills.isEmpty() ? "general software development" : String.join(", ", skills))
                + ". Education: " + education + ".";
    }
}
