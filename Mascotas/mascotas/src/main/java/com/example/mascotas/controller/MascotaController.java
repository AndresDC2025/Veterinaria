package com.example.mascotas.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.mascotas.dto.MascotaResponse;

import com.example.mascotas.dto.ApiResponse;
import com.example.mascotas.dto.MascotaDTO;
import com.example.mascotas.model.Mascota;
import com.example.mascotas.service.MascotaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiResponse<MascotaResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<MascotaResponse>builder()
                        .success(true)
                        .message("Mascota encontrada correctamente")
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiResponse<Mascota>> crear(
            @Valid @RequestBody MascotaDTO dto) {

        Mascota nuevaMascota = service.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<Mascota>builder()
                        .success(true)
                        .message("Mascota creada correctamente")
                        .data(nuevaMascota)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiResponse<List<Mascota>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Mascota>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }
}