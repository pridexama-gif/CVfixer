package com.cvfixer.cv;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class CvDocumentService {

    private final CvDocumentRepository repository;

    public CvDocumentService(CvDocumentRepository repository) {
        this.repository = repository;
    }

    public CvDocument create(CreateCvRequest request) {
        return repository.save(new CvDocument(request.fileName(), request.content()));
    }

    public CvDocument create(String fileName, String content) {
        if (fileName == null || fileName.isBlank()) {
            fileName = "cv.txt";
        }
        if (content == null || content.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CV content cannot be blank");
        }
        return repository.save(new CvDocument(fileName, content));
    }

    @Transactional(readOnly = true)
    public List<CvDocument> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public CvDocument findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CV not found"));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CV not found");
        }
        repository.deleteById(id);
    }
}
