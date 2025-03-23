package com.example.document_management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "document_permission")
@Data
public class DocumentPermission {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @Column(name = "user_id")
    private String userId;

    @Enumerated(EnumType.STRING)
    private PermissionLevel permissionLevel;
}
