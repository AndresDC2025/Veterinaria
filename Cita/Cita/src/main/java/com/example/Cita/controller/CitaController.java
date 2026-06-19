package com.example.Cita.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Cita.dto.ApiResponse;
import com.example.Cita.dto.CitaDTO;
import com.example.Cita.model.Cita;
import com.example.Cita.service.CitaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/citas")
@RequiredArgsConstructor
@Tag(name = "Citas", description = "Operaciones relacionadas con las citas")
public class CitaController {

    private final CitaService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear una cita", description = "Crea una nueva cita en el sistema")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Cita creada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<Cita>> crear(
            @Valid @RequestBody CitaDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<Cita>builder()
                        .success(true)
                        .message("Cita creada correctamente")
                        .data(service.crear(dto, token))
                        .error(null)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Listar todas las citas", description = "Obtiene una lista de todas las citas registradas")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<List<Cita>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Cita>>builder()
                        .success(true)
                        .message("Listado de citas obtenido")
                        .data(service.listar())
                        .error(null)
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Obtener una cita por ID", description = "Obtiene los detalles de una cita específica por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cita encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<EntityModel<ApiResponse<Cita>>> obtener(@PathVariable Long id) {
        ApiResponse<Cita> response = ApiResponse.<Cita>builder()
                .success(true)
                .message("Cita obtenida")
                .data(service.obtener(id))
                .error(null)
                .build();

        EntityModel<ApiResponse<Cita>> recurso = EntityModel.of(response);
        recurso.add(linkTo(methodOn(CitaController.class).obtener(id)).withSelfRel());
        recurso.add(linkTo(methodOn(CitaController.class).listar()).withRel("all"));
        recurso.add(linkTo(methodOn(CitaController.class).actualizar(id, null)).withRel("update"));
        recurso.add(linkTo(methodOn(CitaController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(recurso);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar una cita", description = "Actualiza los datos de una cita existente")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cita actualizada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<ApiResponse<Cita>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CitaDTO dto) {

        return ResponseEntity.ok(
                ApiResponse.<Cita>builder()
                        .success(true)
                        .message("Cita actualizada")
                        .data(service.actualizar(id, dto))
                        .error(null)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar una cita", description = "Elimina una cita del sistema por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cita eliminada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Cita eliminada")
                        .error(null)
                        .build()
        );
    }
}
