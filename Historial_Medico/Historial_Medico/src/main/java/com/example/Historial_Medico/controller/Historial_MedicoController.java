package com.example.Historial_Medico.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Historial_Medico.dto.*;
import com.example.Historial_Medico.service.Historial_MedicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/historiales")
@RequiredArgsConstructor
@Tag(name = "Historial Medico", description = "Operaciones relacionadas con el historial medico")
public class Historial_MedicoController {

    private final Historial_MedicoService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(summary = "Crear un historial medico", description = "Crea un nuevo registro de historial medico")
    public ResponseEntity<ApiResponse<Historial_MedicoResponse>> crear(
            @Valid @RequestBody Historial_MedicoDTO dto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        return ResponseEntity.status(201).body(
                ApiResponse.<Historial_MedicoResponse>builder()
                        .success(true)
                        .message("Historial medico creado correctamente")
                        .data(service.crear(dto, token))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'VETERINARIO')")
    @Operation(summary = "Listar historiales medicos", description = "Obtiene una lista de todos los historiales medicos")
    public ResponseEntity<ApiResponse<List<Historial_MedicoResponse>>> listar(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        return ResponseEntity.ok(
                ApiResponse.<List<Historial_MedicoResponse>>builder()
                        .success(true)
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'VETERINARIO')")
    @Operation(summary = "Obtener historial por ID", description = "Obtiene los detalles de un historial medico por su ID")
    public ResponseEntity<ApiResponse<Historial_MedicoResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        return ResponseEntity.ok(
                ApiResponse.<Historial_MedicoResponse>builder()
                        .success(true)
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(summary = "Actualizar un historial medico", description = "Actualiza los datos de un historial medico existente")
    public ResponseEntity<ApiResponse<Historial_MedicoResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Historial_MedicoDTO dto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        return ResponseEntity.ok(
                ApiResponse.<Historial_MedicoResponse>builder()
                        .success(true)
                        .message("Historial medico actualizado correctamente")
                        .data(service.actualizar(id, dto, token))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Eliminar un historial medico", description = "Elimina un historial medico del sistema por su ID")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Historial medico eliminado correctamente")
                        .build()
        );
    }
}