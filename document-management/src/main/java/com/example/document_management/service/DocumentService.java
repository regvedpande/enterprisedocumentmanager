package com.example.document_management.service;

import com.example.document_management.dto.DocumentDto;
import com.example.document_management.dto.DocumentUploadDto;
import com.example.document_management.entity.Document;
import com.example.document_management.entity.DocumentPermission;
import com.example.document_management.entity.PermissionLevel;
import com.example.document_management.repository.DocumentPermissionRepository;
import com.example.document_management.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentPermissionRepository permissionRepository;

    @Transactional
    public void uploadDocument(DocumentUploadDto uploadDto, String userId) throws IOException {
        Document document = new Document();
        document.setTitle(uploadDto.getTitle());
        document.setFileName(uploadDto.getFile().getOriginalFilename());
        document.setContentType(uploadDto.getFile().getContentType());
        document.setContent(uploadDto.getFile().getBytes());
        document.setCreatedBy(userId);

        DocumentPermission permission = new DocumentPermission();
        permission.setDocument(document);
        permission.setUserId(userId);
        permission.setPermissionLevel(PermissionLevel.WRITE);

        document.getPermissions().add(permission);

        documentRepository.save(document);
    }


    @Transactional(readOnly = true)
    public List<DocumentDto> searchDocuments(String query, String userId) {
        List<DocumentDto> documents = documentRepository.findDocumentsByTitleContainingIgnoreCase(query);
        if (documents.isEmpty()) {
            return List.of();
        }
        List<UUID> documentIds = documents.stream().map(DocumentDto::getId).toList();

        List<DocumentPermission> permissions = permissionRepository.findByDocumentIdIn(documentIds);

        // Filter docs to only those with a matching permission for userId
        return documents.stream()
                .filter(dto -> permissions.stream()
                        .anyMatch(perm -> perm.getDocument().getId().equals(dto.getId()) &&
                                perm.getUserId().equals(userId)))
                .toList();
    }


    @Transactional(readOnly = true)
    public Document getDocumentById(UUID id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));
    }
}
