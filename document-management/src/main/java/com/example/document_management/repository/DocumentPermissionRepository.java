package com.example.document_management.repository;

import com.example.document_management.entity.DocumentPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DocumentPermissionRepository extends JpaRepository<DocumentPermission, UUID> {

    @Query("SELECT p FROM DocumentPermission p WHERE p.document.id IN :documentIds")
    List<DocumentPermission> findByDocumentIdIn(@Param("documentIds") List<UUID> documentIds);
}
