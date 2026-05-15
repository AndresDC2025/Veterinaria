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
    public ResponseEntity<ApiResponse<Tratamientos>> getById(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Tratamientos>builder()
                        .success(true)
                        .message("Tratamiento encontrado")
                        .data(service.getById(id))
                        .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Tratamientos>> crear(
            @Valid @RequestBody TratamientosDTO dto) {

        return ResponseEntity.status(201).body(
                ApiResponse.<Tratamientos>builder()
                        .success(true)
                        .message("Tratamiento creado")
                        .data(service.save(dto))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Tratamientos>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TratamientosDTO dto) {

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
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Tratamiento eliminado")
                        .data("OK")
                        .build()
        );
    }

    @GetMapping("/mascota/{mascotaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<List<Tratamientos>>> listarPorMascota(
            @PathVariable Long mascotaId) {

        return ResponseEntity.ok(
                ApiResponse.<List<Tratamientos>>builder()
                        .success(true)
                        .message("Tratamientos por mascota")
                        .data(service.listarPorMascota(mascotaId))
                        .build()
        );
    }
}