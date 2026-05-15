package com.example.Resena.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Resena.dto.ApiResponse;
import com.example.Resena.dto.ResenaDTO;
import com.example.Resena.model.Resena;
import com.example.Resena.service.ResenaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resena")
public class ResenaController {

    private final ResenaService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<List<Resena>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Resena>>builder()
                        .success(true)
                        .message("Lista de reseñas")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Resena>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Resena>builder()
                        .success(true)
                        .message("Reseña encontrada")
                        .data(service.getById(id))
                        .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Resena>> crear(
            @Valid @RequestBody ResenaDTO dto) {

        return ResponseEntity.status(201).body(
                ApiResponse.<Resena>builder()
                        .success(true)
                        .message("Reseña creada")
                        .data(service.save(dto))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Resena>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ResenaDTO dto) {

        return ResponseEntity.ok(
                ApiResponse.<Resena>builder()
                        .success(true)
                        .message("Reseña actualizada")
                        .data(service.actualizar(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Reseña eliminada")
                        .data("OK")
                        .build()
        );
    }

    @GetMapping("/veterinario/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<List<Resena>>> porVeterinario(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<List<Resena>>builder()
                        .success(true)
                        .message("Reseñas del veterinario")
                        .data(service.listarPorVeterinario(id))
                        .build()
        );
    }
}