package com.example.document_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentUploadDto {

    @NotNull
    private MultipartFile file;

    @NotBlank
    private String title;
}
