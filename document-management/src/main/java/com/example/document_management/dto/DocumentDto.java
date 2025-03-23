package com.example.document_management.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DocumentDto {
    private UUID id;
    private String title;
    private String fileName;
    private Integer version;
    private LocalDateTime createdAt;

    // Required for custom JPQL constructor expressions
    public DocumentDto(UUID id, String title, String fileName, Integer version, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.fileName = fileName;
        this.version = version;
        this.createdAt = createdAt;
    }

    // No-args constructor (Lombok generates it by default if needed)
}
