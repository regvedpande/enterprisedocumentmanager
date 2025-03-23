package com.example.document_management.controller;

import com.example.document_management.dto.DocumentDto;
import com.example.document_management.dto.DocumentUploadDto;
import com.example.document_management.entity.Document;
import com.example.document_management.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        String userId = (principal != null) ? principal.getName() : "anonymous";
        model.addAttribute("uploadDto", new DocumentUploadDto());
        model.addAttribute("documents", documentService.searchDocuments("", userId));
        return "home";
    }

    @PostMapping("/documents/upload")
    public String uploadDocument(@Valid @ModelAttribute("uploadDto") DocumentUploadDto uploadDto,
                                 BindingResult bindingResult,
                                 Model model,
                                 Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            // reload docs
            String userId = (principal != null) ? principal.getName() : "anonymous";
            model.addAttribute("documents", documentService.searchDocuments("", userId));
            return "home";
        }

        String userId = (principal != null) ? principal.getName() : "anonymous";
        documentService.uploadDocument(uploadDto, userId);
        return "redirect:/";
    }


    @GetMapping("/documents/search")
    public String searchDocuments(@RequestParam String query, Model model, Principal principal) {
        String userId = (principal != null) ? principal.getName() : "anonymous";
        List<DocumentDto> documents = documentService.searchDocuments(query, userId);
        model.addAttribute("documents", documents);
        model.addAttribute("uploadDto", new DocumentUploadDto());
        return "home";
    }

    @GetMapping("/documents/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable UUID id, Principal principal) {
        Document document = documentService.getDocumentById(id);
        String userId = (principal != null) ? principal.getName() : "anonymous";

        boolean hasPermission = document.getPermissions().stream()
                .anyMatch(p -> p.getUserId().equals(userId));
        if (!hasPermission) {
            return ResponseEntity.status(403).build();
        }

        ByteArrayResource resource = new ByteArrayResource(document.getContent());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
                .body(resource);
    }
}
