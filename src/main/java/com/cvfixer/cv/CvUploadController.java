package com.cvfixer.cv;

import com.cvfixer.common.CvTextExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cvs")
public class CvUploadController {

    private final CvDocumentService service;
    private final CvTextExtractor textExtractor;

    public CvUploadController(CvDocumentService service, CvTextExtractor textExtractor) {
        this.service = service;
        this.textExtractor = textExtractor;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CvResponse upload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Please upload a CV file");
        }

        String filename = file.getOriginalFilename() == null ? "cv.txt" : file.getOriginalFilename();
        String content = textExtractor.extractText(file);

        if (content.isBlank()) {
            throw new IllegalArgumentException("The uploaded file did not contain readable text");
        }

        return CvResponse.from(service.create(filename, content));
    }
}
