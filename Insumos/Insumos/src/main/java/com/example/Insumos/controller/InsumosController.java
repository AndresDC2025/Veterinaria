package com.example.Insumos.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Insumos.dto.ApiResponse;
import com.example.Insumos.model.Insumos;
import com.example.Insumos.service.InsumosService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class InsumosController {

    @Autowired
    private InsumosService service; 

    @GetMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public ResponseEntity<ApiResponse<Insumos>> getById(@PathVariable Long id) {
    log.info("Buscando Insumos con ID: {}", id);
    Insumos Insumos = service.getById(id);
    ApiResponse<Insumos> response = ApiResponse.<Insumos>builder()
            .success(true)
            .message("Insumos encontrada correctamente")
            .data(Insumos)
            .build();
            
    return ResponseEntity.ok(response);
}

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Insumos> crear(
            @Valid @RequestBody Insumos Insumos,
            @RequestHeader("Authorization") String token) {

        log.info("Creando Insumos: {}", Insumos.getNombre());

        Insumos nuevaInsumos = service.save(Insumos);

        return ResponseEntity.status(201).body(nuevaInsumos);
    }
}