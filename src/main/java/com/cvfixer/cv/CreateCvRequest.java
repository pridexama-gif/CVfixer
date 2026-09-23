package com.cvfixer.cv;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCvRequest(
        @NotBlank @Size(max = 255) String fileName,
        @NotBlank @Size(max = 100_000) String content
) {
}
