package com.example.Tratamientos.controller;

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

import com.example.Tratamientos.dto.ApiResponse;
import com.example.Tratamientos.model.Tratamientos;
import com.example.Tratamientos.service.TratamientosService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/Tratamientos")
public class TratamientosController {

    @Autowired
    private TratamientosService service; 

    @GetMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public ResponseEntity<ApiResponse<Tratamientos>> getById(@PathVariable Long id) {
    log.info("Buscando Tratamientos con ID: {}", id);
    Tratamientos Tratamientos = service.getById(id);
    ApiResponse<Tratamientos> response = ApiResponse.<Tratamientos>builder()
            .success(true)
            .message("Tratamientos encontrada correctamente")
            .data(Tratamientos)
            .build();
            
    return ResponseEntity.ok(response);
}

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Tratamientos> crear(
            @Valid @RequestBody Tratamientos Tratamientos,
            @RequestHeader("Authorization") String token) {

        log.info("Creando Tratamientos: {}", Tratamientos.getNombre());

        Tratamientos nuevaTratamientos = service.save(Tratamientos);

        return ResponseEntity.status(201).body(nuevaTratamientos);
    }
}