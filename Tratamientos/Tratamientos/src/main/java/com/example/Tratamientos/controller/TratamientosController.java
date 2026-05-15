package com.example.Tratamientos.controller;

import java.util.List;

import com.example.Tratamientos.dto.ApiResponse;
import com.example.Tratamientos.dto.TratamientosDTO;
import com.example.Tratamientos.dto.TratamientosResponse;
import com.example.Tratamientos.service.TratamientosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tratamientos")
public class TratamientosController {

    private final TratamientosService service;

    // 🔥 LISTAR
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN','VETERINARIO')")
    public ResponseEntity<ApiResponse<List<TratamientosResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<TratamientosResponse>>builder()
                        .success(true)
                        .message("Lista de tratamientos")
                        .data(service.listar(token))
                        .build()
        );
    }

    // 🔥 OBTENER
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','VETERINARIO')")
    public ResponseEntity<ApiResponse<TratamientosResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<TratamientosResponse>builder()
                        .success(true)
                        .message("Tratamiento encontrado")
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    // 🔥 CREAR
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIO')")
    public ResponseEntity<ApiResponse<TratamientosResponse>> crear(
            @Valid @RequestBody TratamientosDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<TratamientosResponse>builder()
                        .success(true)
                        .message("Tratamiento creado")
                        .data(service.crear(dto, token))
                        .build()
        );
    }

    // 🔥 ACTUALIZAR
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIO')")
    public ResponseEntity<ApiResponse<TratamientosResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TratamientosDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<TratamientosResponse>builder()
                        .success(true)
                        .message("Tratamiento actualizado")
                        .data(service.actualizar(id, dto, token))
                        .build()
        );
    }

    // 🔥 ELIMINAR
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> eliminar(
            @PathVariable Long id) {

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