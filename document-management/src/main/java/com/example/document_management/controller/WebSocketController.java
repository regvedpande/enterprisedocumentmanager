package com.example.document_management.controller;

import com.example.document_management.dto.DocumentDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    // When client sends to /app/uploadNotify, broadcast to /topic/documentUpdates
    @MessageMapping("/uploadNotify")
    @SendTo("/topic/documentUpdates")
    public DocumentDto notifyDocumentUpload(DocumentDto documentDto) {
        // In a real app, you might enrich or validate the data
        return documentDto;
    }
}
