package com.cvfixer.cv;

import java.time.Instant;

public record CvResponse(Long id, String fileName, String content, Instant createdAt) {

    public static CvResponse from(CvDocument cv) {
        return new CvResponse(cv.getId(), cv.getFileName(), cv.getContent(), cv.getCreatedAt());
    }
}
