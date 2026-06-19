package com.example.Resena.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Resena.dto.ApiResponse;
import com.example.Resena.dto.ResenaDTO;
import com.example.Resena.model.Resena;
import com.example.Resena.service.ResenaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resena")
@Tag(name = "Reseñas", description = "Operaciones relacionadas con las reseñas")
public class ResenaController {

    private final ResenaService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    @Operation(summary = "Listar todas las reseñas", description = "Obtiene una lista de todas las reseñas registradas")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<List<Resena>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Resena>>builder()
                        .success(true)
                        .message("Lista de reseñas")
                        .data(service.listar())
                        .error(null)
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    @Operation(summary = "Obtener reseña por ID", description = "Obtiene los detalles de una reseña específica por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reseña encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<EntityModel<ApiResponse<Resena>>> obtener(@PathVariable Long id) {
        ApiResponse<Resena> response = ApiResponse.<Resena>builder()
                .success(true)
                .message("Reseña encontrada")
                .data(service.getById(id))
                .error(null)
                .build();

        EntityModel<ApiResponse<Resena>> recurso = EntityModel.of(response);
        recurso.add(linkTo(methodOn(ResenaController.class).obtener(id)).withSelfRel());
        recurso.add(linkTo(methodOn(ResenaController.class).listar()).withRel("all"));
        recurso.add(linkTo(methodOn(ResenaController.class).actualizar(id, null)).withRel("update"));
        recurso.add(linkTo(methodOn(ResenaController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(recurso);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Crear una reseña", description = "Crea una nueva reseña en el sistema")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Reseña creada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<Resena>> crear(@Valid @RequestBody ResenaDTO dto) {
        return ResponseEntity.status(201).body(
                ApiResponse.<Resena>builder()
                        .success(true)
                        .message("Reseña creada")
                        .data(service.save(dto))
                        .error(null)
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Actualizar una reseña", description = "Actualiza los datos de una reseña existente")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reseña actualizada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<ApiResponse<Resena>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ResenaDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<Resena>builder()
                        .success(true)
                        .message("Reseña actualizada")
                        .data(service.actualizar(id, dto))
                        .error(null)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar una reseña", description = "Elimina una reseña del sistema por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reseña eliminada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Reseña eliminada")
                        .data("OK")
                        .error(null)
                        .build()
        );
    }

    @GetMapping("/veterinario/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    @Operation(summary = "Reseñas por veterinario", description = "Obtiene todas las reseñas de un veterinario específico")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Reseñas encontradas"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<List<Resena>>> porVeterinario(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<List<Resena>>builder()
                        .success(true)
                        .message("Reseñas del veterinario")
                        .data(service.listarPorVeterinario(id))
                        .error(null)
                        .build()
        );
    }
}
