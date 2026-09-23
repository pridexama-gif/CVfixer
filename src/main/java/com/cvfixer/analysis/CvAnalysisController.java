package com.cvfixer.analysis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cvs")
public class CvAnalysisController {

    private final CvAnalysisService analysisService;

    public CvAnalysisController(CvAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/{id}/analysis")
    public CvAnalysisResult analyze(@PathVariable Long id) {
        return analysisService.analyze(id);
    }
}
