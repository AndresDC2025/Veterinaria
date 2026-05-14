package com.example.Veterinarios.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Veterinarios.dto.ApiResponse;
import com.example.Veterinarios.dto.VeterinarioDTO;
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
    public ResponseEntity<ApiResponse<Veterinarios>> obtener(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                ApiResponse.<Veterinarios>builder()
                        .success(true)
                        .message("Veterinario encontrado")
                        .data(servicio.obtener(id))
                        .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Veterinarios>> guardar(
            @Valid @RequestBody VeterinarioDTO dto
    ) {

        Veterinarios veterinario = servicio.guardar(dto);

        return ResponseEntity.status(201)
                .body(
                        ApiResponse.<Veterinarios>builder()
                                .success(true)
                                .message("Veterinario creado")
                                .data(veterinario)
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