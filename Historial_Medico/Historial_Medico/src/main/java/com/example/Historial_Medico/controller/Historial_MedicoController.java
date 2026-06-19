package com.example.Historial_Medico.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Historial_Medico.dto.*;
import com.example.Historial_Medico.service.Historial_MedicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/historiales")
@RequiredArgsConstructor
@Tag(name = "Historial Médico", description = "Operaciones relacionadas con el historial médico")
public class Historial_MedicoController {

    private final Historial_MedicoService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(summary = "Crear un historial médico", description = "Crea un nuevo registro de historial médico")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Historial creado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<Historial_MedicoResponse>> crear(
            @Valid @RequestBody Historial_MedicoDTO dto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.status(201).body(
                ApiResponse.<Historial_MedicoResponse>builder()
                        .success(true)
                        .message("Historial médico creado correctamente")
                        .data(service.crear(dto, token))
                        .error(null)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'VETERINARIO')")
    @Operation(summary = "Listar historiales médicos", description = "Obtiene una lista de todos los historiales médicos")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<List<Historial_MedicoResponse>>> listar(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(
                ApiResponse.<List<Historial_MedicoResponse>>builder()
                        .success(true)
                        .message("Lista de historiales médicos")
                        .data(service.listar(token))
                        .error(null)
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'VETERINARIO')")
    @Operation(summary = "Obtener historial por ID", description = "Obtiene los detalles de un historial médico por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Historial encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    public ResponseEntity<EntityModel<ApiResponse<Historial_MedicoResponse>>> obtener(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        ApiResponse<Historial_MedicoResponse> response = ApiResponse.<Historial_MedicoResponse>builder()
                .success(true)
                .message("Historial médico encontrado")
                .data(service.obtener(id, token))
                .error(null)
                .build();

        EntityModel<ApiResponse<Historial_MedicoResponse>> recurso = EntityModel.of(response);
        recurso.add(linkTo(methodOn(Historial_MedicoController.class).obtener(id, authHeader)).withSelfRel());
        recurso.add(linkTo(methodOn(Historial_MedicoController.class).listar(authHeader)).withRel("all"));
        recurso.add(linkTo(methodOn(Historial_MedicoController.class).actualizar(id, null, authHeader)).withRel("update"));
        recurso.add(linkTo(methodOn(Historial_MedicoController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(recurso);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(summary = "Actualizar un historial médico", description = "Actualiza los datos de un historial médico existente")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Historial actualizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    public ResponseEntity<ApiResponse<Historial_MedicoResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Historial_MedicoDTO dto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(
                ApiResponse.<Historial_MedicoResponse>builder()
                        .success(true)
                        .message("Historial médico actualizado correctamente")
                        .data(service.actualizar(id, dto, token))
                        .error(null)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Eliminar un historial médico", description = "Elimina un historial médico del sistema por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Historial eliminado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Historial médico eliminado correctamente")
                        .error(null)
                        .build()
        );
    }
}
