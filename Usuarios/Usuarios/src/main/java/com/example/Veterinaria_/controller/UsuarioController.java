package com.example.Veterinaria_.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Veterinaria_.dto.*;
import com.example.Veterinaria_.service.UsuarioService;

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
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los Usuarios")
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(
        summary = "Crear un Usuario",
        description = "Crea un Usuario en el sistema"
)
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description =  "Usuario creado correctamente",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UsuarioResponse.class)
                )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Datos invalidos"
        )
})
    public ResponseEntity<ApiResponse<UsuarioResponse>> crear(
            @Valid @RequestBody UsuariosDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<UsuarioResponse>builder()
                        .success(true)
                        .message("Usuario creado")
                        .data(service.crear(dto, token))
                        .build()
        );
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(
              summary = "Obtener todos los Usuarios",
              description = "Obtiene una lista de todos los Usuarios")
     @io.swagger.v3.oas.annotations.responses.ApiResponse(
               responseCode = "200", 
               description = "Usuarios listados exitosamente")
    public ResponseEntity<ApiResponse<List<UsuarioResponse>>> listar(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<List<UsuarioResponse>>builder()
                        .success(true)
                        .data(service.listar(token))
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(
        summary = "Obtener Usuarios mediante ID", 
        description = "Muestra la informacion de un Usuario mediante su respectivo ID"
)
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200", 
                description  = "Usuario encontrado exitosamente",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UsuarioResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404", 
                description  = "Usuario no encontrado")
})
    public ResponseEntity<ApiResponse<UsuarioResponse>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(
                ApiResponse.<UsuarioResponse>builder()
                        .success(true)
                        .data(service.obtener(id, token))
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(
        summary = "Actualizar Usuario",
        description = "Actualiza los datos de un Usuario mediante su respectivo ID"
)
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Informacion actualizada con exito",
                content = @Content( 
                        mediaType = "application/json",
                        schema = @Schema(implementation = UsuarioResponse.class)
                )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Usuario no encontrado"
        )
    })
    public ResponseEntity<ApiResponse<UsuarioResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuariosDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<UsuarioResponse>builder()
                        .success(true)
                        .message("Usuario actualizado")
                        .data(service.actualizar(id, dto, token))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(
    summary = "Eliminar Usuario",
    description = "Elimina un Usuario Mediante su ID"
)

    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Usuario Eliminado correctamente"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Usuario no encontrado"
        )
    })  

    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Usuario eliminado")
                        .build()
        );
    }
}