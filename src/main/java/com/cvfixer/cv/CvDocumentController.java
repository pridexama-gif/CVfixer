package com.cvfixer.cv;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cvs")
public class CvDocumentController {

    private final CvDocumentService service;

    public CvDocumentController(CvDocumentService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CvResponse create(@Valid @RequestBody CreateCvRequest request) {
        return CvResponse.from(service.create(request));
    }

    @GetMapping
    public List<CvResponse> findAll() {
        return service.findAll().stream().map(CvResponse::from).toList();
    }

    @GetMapping("/{id}")
    public CvResponse findById(@PathVariable Long id) {
        return CvResponse.from(service.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
