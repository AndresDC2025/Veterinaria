package com.example.Insumos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Insumos.dto.ApiResponse;
import com.example.Insumos.dto.InsumosDTO;
import com.example.Insumos.model.Insumos;
import com.example.Insumos.service.InsumosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/insumos")
public class InsumosController {

    private final InsumosService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<List<Insumos>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Insumos>>builder()
                        .success(true)
                        .message("Lista de insumos")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Insumos>> getById(
            @PathVariable Long id
    ) {

        Insumos insumo = service.getById(id);

        return ResponseEntity.ok(
                ApiResponse.<Insumos>builder()
                        .success(true)
                        .message("Insumo encontrado")
                        .data(insumo)
                        .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Insumos>> crear(
            @Valid @RequestBody InsumosDTO dto
    ) {

        Insumos nuevoInsumo = service.save(dto);

        return ResponseEntity.status(201)
                .body(
                        ApiResponse.<Insumos>builder()
                                .success(true)
                                .message("Insumo creado")
                                .data(nuevoInsumo)
                                .build()
                );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Insumos>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody InsumosDTO dto
    ) {

        return ResponseEntity.ok(
                ApiResponse.<Insumos>builder()
                        .success(true)
                        .message("Insumo actualizado")
                        .data(service.actualizar(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> eliminar(
            @PathVariable Long id
    ) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Insumo eliminado")
                        .data("OK")
                        .build()
        );
    }
}