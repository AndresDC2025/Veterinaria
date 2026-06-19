package com.example.Veterinaria_.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Veterinaria_.dto.*;
import com.example.Veterinaria_.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Crear un usuario", description = "Crea un nuevo usuario en el sistema")
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
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios registrados")
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
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene los detalles de un usuario por su ID")
    public ResponseEntity<ApiResponse<EntityModel<UsuarioResponse>>> obtener(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        UsuarioResponse usuario = service.obtener(id, token);
        EntityModel<UsuarioResponse> recurso = EntityModel.of(usuario);
        recurso.add(linkTo(methodOn(UsuarioController.class).obtener(id, token)).withSelfRel());
        recurso.add(linkTo(methodOn(UsuarioController.class).listar(token)).withRel("all"));
        recurso.add(linkTo(methodOn(UsuarioController.class).actualizar(id, null, token)).withRel("update"));
        recurso.add(linkTo(methodOn(UsuarioController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(
                ApiResponse.<EntityModel<UsuarioResponse>>builder()
                        .success(true)
                        .data(recurso)
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario por su ID")
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
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID")
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