package com.example.Veterinarios.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Veterinarios.dto.ApiResponse;
import com.example.Veterinarios.dto.VeterinarioDTO;
import com.example.Veterinarios.dto.VeterinarioResponse;
import com.example.Veterinarios.model.Veterinarios;
import com.example.Veterinarios.service.VeterinariosService;

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
@RequestMapping("/api/v1/veterinarios")
@Tag(name = "Veterinarios", description = "Operaciones relacionadas con los veterinarios")
public class VeterinariosController {

    private final VeterinariosService servicio;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    @Operation(summary = "Listar veterinarios", description = "Obtiene una lista de todos los veterinarios registrados")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<List<Veterinarios>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Veterinarios>>builder()
                        .success(true)
                        .message("Lista de veterinarios")
                        .data(servicio.listar())
                        .error(null)
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    @Operation(summary = "Obtener veterinario por ID", description = "Obtiene los detalles de un veterinario por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Veterinario encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Veterinario no encontrado")
    })
    public ResponseEntity<EntityModel<ApiResponse<VeterinarioResponse>>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        log.info("Buscando veterinario con ID: {}", id);

        ApiResponse<VeterinarioResponse> response = ApiResponse.<VeterinarioResponse>builder()
                .success(true)
                .message("Veterinario encontrado correctamente")
                .data(servicio.obtener(id, token))
                .error(null)
                .build();

        EntityModel<ApiResponse<VeterinarioResponse>> recurso = EntityModel.of(response);
        recurso.add(linkTo(methodOn(VeterinariosController.class).obtener(id, token)).withSelfRel());
        recurso.add(linkTo(methodOn(VeterinariosController.class).listar()).withRel("all"));
        recurso.add(linkTo(methodOn(VeterinariosController.class).actualizar(id, null)).withRel("update"));
        recurso.add(linkTo(methodOn(VeterinariosController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(recurso);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(summary = "Crear veterinario", description = "Crea un nuevo veterinario en el sistema")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Veterinario creado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<Veterinarios>> guardar(@Valid @RequestBody VeterinarioDTO dto) {
        return ResponseEntity.status(201).body(
                ApiResponse.<Veterinarios>builder()
                        .success(true)
                        .message("Veterinario creado")
                        .data(servicio.guardar(dto))
                        .error(null)
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(summary = "Actualizar veterinario", description = "Actualiza los datos de un veterinario existente")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Veterinario actualizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Veterinario no encontrado")
    })
    public ResponseEntity<ApiResponse<Veterinarios>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VeterinarioDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<Veterinarios>builder()
                        .success(true)
                        .message("Veterinario actualizado")
                        .data(servicio.actualizar(id, dto))
                        .error(null)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar veterinario", description = "Elimina un veterinario del sistema por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Veterinario eliminado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Veterinario no encontrado")
    })
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Veterinario eliminado")
                        .data("OK")
                        .error(null)
                        .build()
        );
    }
}
