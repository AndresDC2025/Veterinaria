package com.example.Tratamientos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Tratamientos.dto.ApiResponse;
import com.example.Tratamientos.dto.TratamientosDTO;
import com.example.Tratamientos.model.Tratamientos;
import com.example.Tratamientos.service.TratamientosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tratamientos")
public class TratamientosController {

    private final TratamientosService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<List<Tratamientos>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Tratamientos>>builder()
                        .success(true)
                        .message("Lista de tratamientos")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Tratamientos>> getById(
            @PathVariable Long id
    ) {

        Tratamientos tratamiento = service.getById(id);

        return ResponseEntity.ok(
                ApiResponse.<Tratamientos>builder()
                        .success(true)
                        .message("Tratamiento encontrado")
                        .data(tratamiento)
                        .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Tratamientos>> crear(
            @Valid @RequestBody TratamientosDTO dto
    ) {

        Tratamientos tratamiento = service.save(dto);

        return ResponseEntity.status(201)
                .body(
                        ApiResponse.<Tratamientos>builder()
                                .success(true)
                                .message("Tratamiento creado")
                                .data(tratamiento)
                                .build()
                );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Tratamientos>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TratamientosDTO dto
    ) {

        return ResponseEntity.ok(
                ApiResponse.<Tratamientos>builder()
                        .success(true)
                        .message("Tratamiento actualizado")
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
                        .message("Tratamiento eliminado")
                        .data("OK")
                        .build()
        );
    }
}