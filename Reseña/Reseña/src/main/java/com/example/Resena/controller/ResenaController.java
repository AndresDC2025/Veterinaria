package com.example.Resena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Resena.dto.ApiResponse;
import com.example.Resena.model.Resena;
import com.example.Resena.service.ResenaService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/Resena")
public class ResenaController {

    @Autowired
    private ResenaService service; 

    @GetMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public ResponseEntity<ApiResponse<Resena>> getById(@PathVariable Long id) {
    log.info("Buscando Resena con ID: {}", id);
    Resena Resena = service.getById(id);
    ApiResponse<Resena> response = ApiResponse.<Resena>builder()
            .success(true)
            .message("Resena encontrada correctamente")
            .data(Resena)
            .build();
            
    return ResponseEntity.ok(response);
}

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Resena> crear(
            @Valid @RequestBody Resena Resena,
            @RequestHeader("Authorization") String token) {

        log.info("Creando Resena: {}", Resena.getId());

        Resena nuevaResena = service.save(Resena);

        return ResponseEntity.status(201).body(nuevaResena);
    }
}