package com.fundoonotes.fundoo_notes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
                "status", "UP",
                "message", "Fundoo Notes Backend API is running successfully!",
                "swaggerUrl", "https://fundo-notes-backend.onrender.com/swagger-ui.html",
                "frontendUrl", "https://fundo-notes-frontend.vercel.app"
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
