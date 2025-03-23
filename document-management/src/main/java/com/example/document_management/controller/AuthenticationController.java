package com.example.document_management.controller;

import com.example.document_management.security.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // DEMO ONLY: Hardcoded check
        if ("password".equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new LoginResponse(token));
        }
        // Invalid credentials
        return ResponseEntity.status(401).build();
    }
}

@Data
class LoginRequest {
    private String username;
    private String password;
}

@Data
class LoginResponse {
    private final String token;
}
