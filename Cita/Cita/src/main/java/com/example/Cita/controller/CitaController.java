package com.example.Cita.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Cita.dto.ApiResponse;
import com.example.Cita.dto.CitaDTO;
import com.example.Cita.model.Cita;
import com.example.Cita.service.CitaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Cita>> crear(
            @Valid @RequestBody CitaDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<Cita>builder()
                        .success(true)
                        .message("Cita creada correctamente")
                        .data(service.crear(dto, token))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Cita>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Cita>>builder()
                        .success(true)
                        .message("Listado de citas obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<Cita>> obtener(@PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.<Cita>builder()
                        .success(true)
                        .message("Cita obtenida")
                        .data(service.obtener(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Cita>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CitaDTO dto) {

        return ResponseEntity.ok(
                ApiResponse.<Cita>builder()
                        .success(true)
                        .message("Cita actualizada")
                        .data(service.actualizar(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Cita eliminada")
                        .build()
        );
    }
}