package com.example.Inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Inventario.dto.ApiResponse;
import com.example.Inventario.dto.InventarioDTO;
import com.example.Inventario.model.Inventario;
import com.example.Inventario.service.InventarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventario")
public class InventarioController {

    private final InventarioService servicio;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<List<Inventario>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Inventario>>builder()
                        .success(true)
                        .message("Lista de inventario")
                        .data(servicio.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Inventario>> obtener(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                ApiResponse.<Inventario>builder()
                        .success(true)
                        .message("Insumo encontrado")
                        .data(servicio.obtener(id))
                        .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Inventario>> guardar(
            @Valid @RequestBody InventarioDTO dto
    ) {

        log.info("Guardando insumo");

        Inventario inventario = servicio.guardar(dto);

        return ResponseEntity.status(201)
                .body(
                        ApiResponse.<Inventario>builder()
                                .success(true)
                                .message("Insumo creado correctamente")
                                .data(inventario)
                                .build()
                );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Inventario>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody InventarioDTO dto
    ) {

        return ResponseEntity.ok(
                ApiResponse.<Inventario>builder()
                        .success(true)
                        .message("Inventario actualizado")
                        .data(servicio.actualizar(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> eliminar(
            @PathVariable Integer id
    ) {

        servicio.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Inventario eliminado")
                        .data("OK")
                        .build()
        );
    }
}