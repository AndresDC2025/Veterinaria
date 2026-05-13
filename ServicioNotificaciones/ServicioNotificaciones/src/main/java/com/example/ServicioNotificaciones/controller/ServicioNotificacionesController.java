package com.example.ServicioNotificaciones.controller;


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

import com.example.ServicioNotificaciones.dto.ApiResponse;
import com.example.ServicioNotificaciones.model.ServicioNotificaciones;
import com.example.ServicioNotificaciones.service.ServicioNotificacionesService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/ServicioNotificaciones")
public class ServicioNotificacionesController {

    @Autowired
    private ServicioNotificacionesService service; 

    @GetMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public ResponseEntity<ApiResponse<ServicioNotificaciones>> getById(@PathVariable Long id) {
    log.info("Buscando ServicioNotificaciones con ID: {}", id);
    ServicioNotificaciones ServicioNotificaciones = service.getById(id);
    ApiResponse<ServicioNotificaciones> response = ApiResponse.<ServicioNotificaciones>builder()
            .success(true)
            .message("ServicioNotificaciones encontrada correctamente")
            .data(ServicioNotificaciones)
            .build();
            
    return ResponseEntity.ok(response);
}

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ServicioNotificaciones> crear(
            @Valid @RequestBody ServicioNotificaciones ServicioNotificaciones,
            @RequestHeader("Authorization") String token) {

        log.info("Creando ServicioNotificaciones: {}", ServicioNotificaciones.getNombre());

        ServicioNotificaciones nuevaServicioNotificaciones = service.save(ServicioNotificaciones);

        return ResponseEntity.status(201).body(nuevaServicioNotificaciones);
    }
}