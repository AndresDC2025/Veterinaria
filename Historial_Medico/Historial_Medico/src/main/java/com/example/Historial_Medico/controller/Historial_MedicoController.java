package com.example.Historial_Medico.controller;


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

import com.example.Historial_Medico.dto.ApiResponse;
import com.example.Historial_Medico.model.Historial_Medico;
import com.example.Historial_Medico.service.Historial_MedicoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/Historial_Medico")
public class Historial_MedicoController {

    @Autowired
    private Historial_MedicoService service; 

    @GetMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public ResponseEntity<ApiResponse<Historial_Medico>> getById(@PathVariable Long id) {
    log.info("Buscando Historial_Medico con ID: {}", id);
    Historial_Medico Historial_Medico = service.getById(id);
    ApiResponse<Historial_Medico> response = ApiResponse.<Historial_Medico>builder()
            .success(true)
            .message("Historial_Medico encontrada correctamente")
            .data(Historial_Medico)
            .build();
            
    return ResponseEntity.ok(response);
}

    /* @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Historial_Medico> crear(
            @Valid @RequestBody Historial_Medico Historial_Medico,
            @RequestHeader("Authorization") String token) {

        log.info("Creando Historial_Medico: {}", Historial_Medico.ById());

        Historial_Medico nuevaHistorial_Medico = service.save(Historial_Medico);

        return ResponseEntity.status(201).body(nuevaHistorial_Medico);
    } */
}