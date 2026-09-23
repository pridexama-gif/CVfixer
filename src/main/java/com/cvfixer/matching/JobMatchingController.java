package com.cvfixer.matching;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JobMatchingController {

    private final JobDescriptionRepository jobDescriptionRepository;
    private final JobMatchingService jobMatchingService;

    public JobMatchingController(JobDescriptionRepository jobDescriptionRepository,
                                JobMatchingService jobMatchingService) {
        this.jobDescriptionRepository = jobDescriptionRepository;
        this.jobMatchingService = jobMatchingService;
    }

    @PostMapping("/jobs")
    @ResponseStatus(HttpStatus.CREATED)
    public JobDescriptionResponse createJob(@RequestBody JobDescriptionRequest request) {
        JobDescription job = new JobDescription(
                request.title(),
                request.description(),
                request.requiredSkills(),
                request.minExperienceYears()
        );

        JobDescription saved = jobDescriptionRepository.save(job);
        return JobDescriptionResponse.from(saved);
    }

    @GetMapping("/jobs/{jobId}/match/{cvId}")
    public JobMatchResponse match(@PathVariable Long jobId, @PathVariable Long cvId) {
        return jobMatchingService.match(jobId, cvId);
    }
}
