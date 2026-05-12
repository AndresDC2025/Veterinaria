package com.example.Veterinarios.controller;

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

import com.example.Veterinarios.model.Veterinarios;
import com.example.Veterinarios.service.VeterinariosService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/veterinarios")
public class VeterinariosController {

    @Autowired
    private VeterinariosService service; 

    @GetMapping("/{id}")
    public Veterinarios getById(@PathVariable Long id) {
        // Este método es el que busca la veterinario 1 y la devuelve
        return service.getById(id); 
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Veterinarios> crear(
            @Valid @RequestBody Veterinarios veterinario,
            @RequestHeader("Authorization") String token) {

        log.info("Creando veterinario: {}", veterinario.getNombre());

        Veterinarios nuevaveterinario = service.save(veterinario);

        return ResponseEntity.status(201).body(nuevaveterinario);
    }
}