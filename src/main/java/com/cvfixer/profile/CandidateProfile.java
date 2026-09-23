package com.cvfixer.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidate_profiles")
public class CandidateProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long cvId;

    @Column(nullable = false, length = 255)
    private String fullName;

    @Column(length = 255)
    private String email;

    @Column(length = 255)
    private String phone;

    @Column(length = 512)
    private String skills;

    @Column
    private Integer experienceYears;

    @Column(length = 512)
    private String education;

    @Column(length = 1024)
    private String summary;

    protected CandidateProfile() {
    }

    public CandidateProfile(Long cvId, String fullName, String email, String phone,
                            String skills, Integer experienceYears, String education, String summary) {
        this.cvId = cvId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.skills = skills;
        this.experienceYears = experienceYears;
        this.education = education;
        this.summary = summary;
    }

    public Long getId() { return id; }
    public Long getCvId() { return cvId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getSkills() { return skills; }
    public Integer getExperienceYears() { return experienceYears; }
    public String getEducation() { return education; }
    public String getSummary() { return summary; }
}
