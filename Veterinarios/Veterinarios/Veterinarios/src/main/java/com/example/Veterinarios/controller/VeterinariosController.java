package com.example.Veterinarios.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Veterinarios.dto.ApiResponse;
import com.example.Veterinarios.dto.VeterinarioDTO;
import com.example.Veterinarios.dto.VeterinarioResponse;
import com.example.Veterinarios.model.Veterinarios;
import com.example.Veterinarios.service.VeterinariosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/veterinarios")
@Tag(name= "Veterinarios", description = "Operaciones relacionadas con los veterianrios")
public class VeterinariosController {

    private final VeterinariosService servicio;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    @Operation(summary = "Obtener todos los veterinarios", description = "Obtiene una lista de todos los veterinarios")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Veterinarios listados exitosamente")
    public ResponseEntity<ApiResponse<List<Veterinarios>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.<List<Veterinarios>>builder()
                        .success(true)
                        .message("Lista de veterinarios")
                        .data(servicio.listar())
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    @Operation(summary = "Obtener Veterinarios mediante ID", description = "Muestra la informacion de un Veterinario mediante su respectivo ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description  = "Veterinario encontrado exitosamente",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = VeterinarioResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description  = "Veterinario no encontrado")

    })
    public ResponseEntity<ApiResponse<VeterinarioResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {

        log.info("Buscando veterinario con ID: {}", id);

        return ResponseEntity.ok(
                ApiResponse.<VeterinarioResponse>builder()
                        .success(true)
                        .message("Veterinario encontrado correctamente")
                        .data(servicio.obtener(id, token))
                        .build()
        );
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(
        summary = "Crear un Veterinario",
        description = "Crea un Veterinario en el sistema"
)
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description =  "Veterinario creado correctamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = VeterinarioResponse.class)
                )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Datos invalidos"
        )
})

    public ResponseEntity<ApiResponse<Veterinarios>> guardar(
            @Valid @RequestBody VeterinarioDTO dto
    ) {

        return ResponseEntity.status(201)
                .body(
                        ApiResponse.<Veterinarios>builder()
                                .success(true)
                                .message("Veterinario creado")
                                .data(servicio.guardar(dto))
                                .build()
                );
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(
        summary = "Actualizar Veterinario",
        description = "Actualiza los datos de un Veterinario mediante su respectivo ID"
)
@ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description =  "Informacion actualizada con exito",
                content = @Content(
                        mediaType = "Aplication/json",
                        schema = @Schema(implementation = VeterinarioResponse.class)
                )
        )
})
    public ResponseEntity<ApiResponse<Veterinarios>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VeterinarioDTO dto
    ) {

        return ResponseEntity.ok(
                ApiResponse.<Veterinarios>builder()
                        .success(true)
                        .message("Veterinario actualizado")
                        .data(servicio.actualizar(id, dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Eliminar Veterinario",
        description = "Elimina un Veterinario Mediante su ID"

    )

    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Veterinario Eliminado correctamente"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Veterinario no encontrado"
        )
    })
    public ResponseEntity<ApiResponse<String>> eliminar(
            @PathVariable Long id
    ) {
        servicio.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Veterinario eliminado")
                        .data("OK")
                        .build()
        );
    }
}