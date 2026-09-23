package com.cvfixer.cv;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/cvs")
public class CvUploadController {

    private final CvDocumentService service;

    public CvUploadController(CvDocumentService service) {
        this.service = service;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CvResponse upload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Please upload a CV file");
        }

        try {
            String filename = file.getOriginalFilename() == null ? "cv.txt" : file.getOriginalFilename();
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            return CvResponse.from(service.create(filename, content));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read uploaded file", e);
        }
    }
}
