package com.example.Tratamientos.controller;

import java.util.List;

import com.example.Tratamientos.dto.ApiResponse;
import com.example.Tratamientos.dto.TratamientosDTO;
import com.example.Tratamientos.dto.TratamientosResponse;
import com.example.Tratamientos.service.TratamientosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tratamientos")
@Tag(name = "Tratamientos", description = "Operaciones relacionadas con los tratamientos")
public class TratamientosController {

    private final TratamientosService service;


    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN','VETERINARIO')")
    @Operation(summary = "Listar todos los tratamientos", description = "Obtiene una lista de todos los tratamientos registrados")
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


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','VETERINARIO')")
    @Operation(summary = "Obtener tratamiento por ID", description = "Obtiene los detalles de un tratamiento especifico por su ID")
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


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIO')")
    @Operation(summary = "Crear un tratamiento", description = "Crea un nuevo tratamiento en el sistema")
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

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIO')")
    @Operation(summary = "Actualizar un tratamiento", description = "Actualiza los datos de un tratamiento existente")
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar un tratamiento", description = "Elimina un tratamiento del sistema por su ID")
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