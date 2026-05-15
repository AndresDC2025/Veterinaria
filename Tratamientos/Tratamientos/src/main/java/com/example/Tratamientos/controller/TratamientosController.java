package com.example.Tratamientos.controller;

import java.util.List;

import com.example.Tratamientos.dto.ApiResponse;
import com.example.Tratamientos.dto.TratamientosDTO;
import com.example.Tratamientos.dto.TratamientosResponse;
import com.example.Tratamientos.service.TratamientosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
=======
import com.example.Tratamientos.dto.ApiResponse;
import com.example.Tratamientos.dto.TratamientosDTO;
import com.example.Tratamientos.model.Tratamientos;
import com.example.Tratamientos.service.TratamientosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

>>>>>>> aa98e270dfe1e4235839e43a47b993446df29bb7
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tratamientos")
public class TratamientosController {

    private final TratamientosService service;

    // 🔥 LISTAR
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN','VETERINARIO')")
    public ResponseEntity<ApiResponse<List<TratamientosResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<TratamientosResponse>>builder()
                        .success(true)
                        .message("Lista de tratamientos")
                        .data(service.listar(token))
                        .build()
        );
    }

    // 🔥 OBTENER
    @GetMapping("/{id}")
<<<<<<< HEAD
    @PreAuthorize("hasAnyRole('USER','ADMIN','VETERINARIO')")
    public ResponseEntity<ApiResponse<TratamientosResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
=======
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Tratamientos>> getById(@PathVariable Long id) {
>>>>>>> aa98e270dfe1e4235839e43a47b993446df29bb7

        return ResponseEntity.ok(
                ApiResponse.<TratamientosResponse>builder()
                        .success(true)
                        .message("Tratamiento encontrado")
<<<<<<< HEAD
                        .data(service.obtener(id, token))
=======
                        .data(service.getById(id))
>>>>>>> aa98e270dfe1e4235839e43a47b993446df29bb7
                        .build()
        );
    }

    // 🔥 CREAR
    @PostMapping
<<<<<<< HEAD
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIO')")
    public ResponseEntity<ApiResponse<TratamientosResponse>> crear(
            @Valid @RequestBody TratamientosDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<TratamientosResponse>builder()
                        .success(true)
                        .message("Tratamiento creado")
                        .data(service.crear(dto, token))
=======
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<Tratamientos>> crear(
            @Valid @RequestBody TratamientosDTO dto) {

        return ResponseEntity.status(201).body(
                ApiResponse.<Tratamientos>builder()
                        .success(true)
                        .message("Tratamiento creado")
                        .data(service.save(dto))
>>>>>>> aa98e270dfe1e4235839e43a47b993446df29bb7
                        .build()
        );
    }

    // 🔥 ACTUALIZAR
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIO')")
    public ResponseEntity<ApiResponse<TratamientosResponse>> actualizar(
            @PathVariable Long id,
<<<<<<< HEAD
            @Valid @RequestBody TratamientosDTO dto,
            @RequestHeader("Authorization") String token) {
=======
            @Valid @RequestBody TratamientosDTO dto) {
>>>>>>> aa98e270dfe1e4235839e43a47b993446df29bb7

        return ResponseEntity.ok(
                ApiResponse.<TratamientosResponse>builder()
                        .success(true)
                        .message("Tratamiento actualizado")
                        .data(service.actualizar(id, dto, token))
                        .build()
        );
    }

    // 🔥 ELIMINAR
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
<<<<<<< HEAD
    public ResponseEntity<ApiResponse<String>> eliminar(
            @PathVariable Long id) {
=======
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Long id) {
>>>>>>> aa98e270dfe1e4235839e43a47b993446df29bb7

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Tratamiento eliminado")
                        .data("OK")
                        .build()
        );
    }

    @GetMapping("/mascota/{mascotaId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    public ResponseEntity<ApiResponse<List<Tratamientos>>> listarPorMascota(
            @PathVariable Long mascotaId) {

        return ResponseEntity.ok(
                ApiResponse.<List<Tratamientos>>builder()
                        .success(true)
                        .message("Tratamientos por mascota")
                        .data(service.listarPorMascota(mascotaId))
                        .build()
        );
    }
}