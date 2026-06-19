package com.example.mascotas.controller;

import java.util.List;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.mascotas.dto.MascotaResponse;

import com.example.mascotas.dto.ApiResponse;
import com.example.mascotas.dto.MascotaDTO;
import com.example.mascotas.model.Mascota;
import com.example.mascotas.service.MascotaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/mascotas")
@RequiredArgsConstructor
@Tag(name = "Mascotas", description = "Operaciones relacionadas con las mascotas")
public class MascotaController {

    private final MascotaService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Obtener mascota por ID", description = "Obtiene los detalles de una mascota por su ID")
    public ResponseEntity<ApiResponse<EntityModel<MascotaResponse>>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        MascotaResponse mascota = service.obtener(id, token);
        EntityModel<MascotaResponse> recurso = EntityModel.of(mascota);
        recurso.add(linkTo(methodOn(MascotaController.class).obtener(id, token)).withSelfRel());
        recurso.add(linkTo(methodOn(MascotaController.class).listar()).withRel("all"));
        recurso.add(linkTo(methodOn(MascotaController.class).actualizar(id, null, token)).withRel("update"));
        recurso.add(linkTo(methodOn(MascotaController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(
                ApiResponse.<EntityModel<MascotaResponse>>builder()
                        .success(true)
                        .message("Mascota encontrada correctamente")
                        .data(recurso)
                        .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Crear una mascota", description = "Crea una nueva mascota en el sistema")
    public ResponseEntity<ApiResponse<Mascota>> crear(
            @Valid @RequestBody MascotaDTO dto) {

        Mascota nuevaMascota = service.crear(dto);

        return ResponseEntity.status(201).body(
                ApiResponse.<Mascota>builder()
                        .success(true)
                        .message("Mascota creada correctamente")
                        .data(nuevaMascota)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Listar todas las mascotas", description = "Obtiene una lista de todas las mascotas registradas")
    public ResponseEntity<ApiResponse<List<Mascota>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Mascota>>builder()
                        .success(true)
                        .message("Listado obtenido")
                        .data(service.listar())
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Actualizar mascota", description = "Actualiza los datos de una mascota por su ID")
    public ResponseEntity<ApiResponse<Mascota>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MascotaDTO dto,
            @RequestHeader("Authorization") String token) {

        Mascota mascota = service.actualizar(id, dto);
        return ResponseEntity.ok(
                ApiResponse.<Mascota>builder()
                        .success(true)
                        .message("Mascota actualizada correctamente")
                        .data(mascota)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Eliminar mascota", description = "Elimina una mascota por su ID")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        service.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Mascota eliminada correctamente")
                        .build()
        );
    }
}
