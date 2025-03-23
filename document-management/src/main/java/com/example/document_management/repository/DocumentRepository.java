package com.example.document_management.repository;

import com.example.document_management.dto.DocumentDto;
import com.example.document_management.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {

    List<Document> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT new com.example.document_management.dto.DocumentDto(d.id, d.title, d.fileName, d.version, d.createdAt) " +
            "FROM Document d " +
            "WHERE UPPER(d.title) LIKE UPPER(CONCAT('%', :title, '%'))")
    List<DocumentDto> findDocumentsByTitleContainingIgnoreCase(@Param("title") String title);
}
