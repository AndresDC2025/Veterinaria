package com.example.mascotas.controller;

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

import com.example.mascotas.dto.ApiResponse;
import com.example.mascotas.model.Mascota;
import com.example.mascotas.service.MascotaService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService service; 

    @GetMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public ResponseEntity<ApiResponse<Mascota>> getById(@PathVariable Long id) {
    log.info("Buscando mascota con ID: {}", id);
    Mascota mascota = service.getById(id);
    ApiResponse<Mascota> response = ApiResponse.<Mascota>builder()
            .success(true)
            .message("Mascota encontrada correctamente")
            .data(mascota)
            .build();
            
    return ResponseEntity.ok(response);
}

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Mascota> crear(
            @Valid @RequestBody Mascota mascota,
            @RequestHeader("Authorization") String token) {

        log.info("Creando mascota: {}", mascota.getNombre());

        Mascota nuevaMascota = service.save(mascota);

        return ResponseEntity.status(201).body(nuevaMascota);
    }
}