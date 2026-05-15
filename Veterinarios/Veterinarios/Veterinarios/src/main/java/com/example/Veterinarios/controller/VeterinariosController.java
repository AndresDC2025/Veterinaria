package com.example.Veterinarios.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Veterinarios.dto.ApiResponse;
import com.example.Veterinarios.dto.VeterinarioDTO;
import com.example.Veterinarios.dto.VeterinarioResponse;
import com.example.Veterinarios.model.Veterinarios;
import com.example.Veterinarios.service.VeterinariosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/veterinarios")
public class VeterinariosController {

    private final VeterinariosService servicio;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<List<Veterinarios>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Veterinarios>>builder()
                        .success(true)
                        .message("Lista de veterinarios")
                        .data(servicio.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<VeterinarioResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {

        log.info("Buscando veterinario con ID: {}", id);

        return ResponseEntity.ok(
                ApiResponse.<VeterinarioResponse>builder()
                        .success(true)
                        .message("Veterinario encontrado correctamente")
                        .data(servicio.obtener(id, token))
                        .build()
        );
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Veterinarios>> guardar(
            @Valid @RequestBody VeterinarioDTO dto
    ) {

        return ResponseEntity.status(201)
                .body(
                        ApiResponse.<Veterinarios>builder()
                                .success(true)
                                .message("Veterinario creado")
                                .data(servicio.guardar(dto))
                                .build()
                );
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Veterinarios>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VeterinarioDTO dto
    ) {

        return ResponseEntity.ok(
                ApiResponse.<Veterinarios>builder()
                        .success(true)
                        .message("Veterinario actualizado")
                        .data(servicio.actualizar(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> eliminar(
            @PathVariable Long id
    ) {

        servicio.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Veterinario eliminado")
                        .data("OK")
                        .build()
        );
    }
}