package com.example.Facturacion.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Facturacion.dto.*;
import com.example.Facturacion.service.FacturacionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/facturacion")
@RequiredArgsConstructor
public class FacturacionController {

    private final FacturacionService service;

    @PostMapping("/usuario/{usuarioId}/cita/{idCita}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<FacturaResponse>> crear(
            @PathVariable Integer usuarioId,
            @PathVariable Integer idCita,
            @Valid @RequestBody FacturacionDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<FacturaResponse>builder()
                        .success(true)
                        .message("Factura creada correctamente")
                        .data(service.crear(dto, usuarioId, idCita, token))
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<List<FacturaResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<FacturaResponse>>builder()
                        .success(true)
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<FacturaResponse>> obtener(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<FacturaResponse>builder()
                        .success(true)
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<FacturaResponse>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody FacturacionDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<FacturaResponse>builder()
                        .success(true)
                        .message("Factura actualizada")
                        .data(service.actualizar(id, dto, token))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Factura eliminada")
                        .build()
        );
    }
}