package com.cvfixer.matching;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_descriptions")
public class JobDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(length = 1000)
    private String requiredSkills;

    @Column
    private Integer minExperienceYears;

    protected JobDescription() {
    }

    public JobDescription(String title, String description, String requiredSkills, Integer minExperienceYears) {
        this.title = title;
        this.description = description;
        this.requiredSkills = requiredSkills;
        this.minExperienceYears = minExperienceYears;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getRequiredSkills() { return requiredSkills; }
    public Integer getMinExperienceYears() { return minExperienceYears; }
}
