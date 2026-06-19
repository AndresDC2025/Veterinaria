package com.example.Tratamientos.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Tratamientos.dto.ApiResponse;
import com.example.Tratamientos.dto.TratamientosDTO;
import com.example.Tratamientos.dto.TratamientosResponse;
import com.example.Tratamientos.service.TratamientosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tratamientos")
@Tag(name = "Tratamientos", description = "Operaciones relacionadas con los tratamientos")
public class TratamientosController {

    private final TratamientosService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN','VETERINARIO')")
    @Operation(summary = "Listar tratamientos", description = "Obtiene una lista de todos los tratamientos")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<List<TratamientosResponse>>> listar(
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<List<TratamientosResponse>>builder()
                        .success(true)
                        .message("Lista de tratamientos")
                        .data(service.listar(token))
                        .error(null)
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN','VETERINARIO')")
    @Operation(summary = "Obtener tratamiento por ID", description = "Obtiene los detalles de un tratamiento por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tratamiento encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Tratamiento no encontrado")
    })
    public ResponseEntity<EntityModel<ApiResponse<TratamientosResponse>>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        ApiResponse<TratamientosResponse> response = ApiResponse.<TratamientosResponse>builder()
                .success(true)
                .message("Tratamiento encontrado")
                .data(service.obtener(id, token))
                .error(null)
                .build();

        EntityModel<ApiResponse<TratamientosResponse>> recurso = EntityModel.of(response);
        recurso.add(linkTo(methodOn(TratamientosController.class).obtener(id, token)).withSelfRel());
        recurso.add(linkTo(methodOn(TratamientosController.class).listar(token)).withRel("all"));
        recurso.add(linkTo(methodOn(TratamientosController.class).actualizar(id, null, token)).withRel("update"));
        recurso.add(linkTo(methodOn(TratamientosController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(recurso);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIO')")
    @Operation(summary = "Crear un tratamiento", description = "Crea un nuevo tratamiento en el sistema")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Tratamiento creado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<TratamientosResponse>> crear(
            @Valid @RequestBody TratamientosDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<TratamientosResponse>builder()
                        .success(true)
                        .message("Tratamiento creado")
                        .data(service.crear(dto, token))
                        .error(null)
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VETERINARIO')")
    @Operation(summary = "Actualizar un tratamiento", description = "Actualiza los datos de un tratamiento existente")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tratamiento actualizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Tratamiento no encontrado")
    })
    public ResponseEntity<ApiResponse<TratamientosResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TratamientosDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<TratamientosResponse>builder()
                        .success(true)
                        .message("Tratamiento actualizado")
                        .data(service.actualizar(id, dto, token))
                        .error(null)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar un tratamiento", description = "Elimina un tratamiento del sistema por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Tratamiento eliminado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Tratamiento no encontrado")
    })
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Tratamiento eliminado")
                        .data("OK")
                        .error(null)
                        .build()
        );
    }
}
