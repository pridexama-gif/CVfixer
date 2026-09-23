package com.cvfixer.common;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class CvTextExtractor {

    private final Tika tika = new Tika();

    public String extractText(MultipartFile file) {
        try {
            String extracted = tika.parseToString(file.getInputStream());
            return extracted == null ? "" : extracted.trim();
        } catch (IOException | TikaException e) {
            throw new IllegalStateException("Failed to parse uploaded CV file: " + file.getOriginalFilename(), e);
        }
    }

    public String extractText(byte[] bytes, String fileName) {
        try {
            String extracted = tika.parseToString(new java.io.ByteArrayInputStream(bytes));
            return extracted == null ? "" : extracted.trim();
        } catch (IOException | TikaException e) {
            throw new IllegalStateException("Failed to parse CV file: " + fileName, e);
        }
    }
}
