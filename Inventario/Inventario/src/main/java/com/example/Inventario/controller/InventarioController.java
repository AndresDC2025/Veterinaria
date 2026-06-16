package com.example.Inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Inventario.dto.ApiResponse;
import com.example.Inventario.dto.InventarioDTO;
import com.example.Inventario.model.Inventario;
import com.example.Inventario.service.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "Operaciones relacionadas con el inventario")
public class InventarioController {

    private final InventarioService servicio;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    @Operation(summary = "Listar inventario", description = "Obtiene una lista de todos los insumos del inventario")
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
    @Operation(summary = "Obtener insumo por ID", description = "Obtiene los detalles de un insumo del inventario por su ID")
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
    @Operation(summary = "Crear un insumo", description = "Agrega un nuevo insumo al inventario")
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
    @Operation(summary = "Actualizar un insumo", description = "Actualiza los datos de un insumo existente en el inventario")
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
    @Operation(summary = "Eliminar un insumo", description = "Elimina un insumo del inventario por su ID")
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

    @PutMapping("/descontar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(summary = "Descontar stock", description = "Descuenta una cantidad del stock de un insumo")
    public ResponseEntity<ApiResponse<Void>> descontarStock(
            @PathVariable Integer id,
            @RequestParam Integer cantidad
    ) {

        servicio.descontarStock(id, cantidad);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Stock actualizado")
                        .build()
        );
    }
}