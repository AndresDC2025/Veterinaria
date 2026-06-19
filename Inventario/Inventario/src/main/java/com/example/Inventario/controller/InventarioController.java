package com.example.Inventario.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Inventario.dto.ApiResponse;
import com.example.Inventario.dto.InventarioDTO;
import com.example.Inventario.model.Inventario;
import com.example.Inventario.service.InventarioService;

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
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "Operaciones relacionadas con el inventario")
public class InventarioController {

    private final InventarioService servicio;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    @Operation(summary = "Listar inventario", description = "Obtiene una lista de todos los insumos del inventario")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<List<Inventario>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Inventario>>builder()
                        .success(true)
                        .message("Lista de inventario")
                        .data(servicio.listar())
                        .error(null)
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'VETERINARIO')")
    @Operation(summary = "Obtener insumo por ID", description = "Obtiene los detalles de un insumo del inventario por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Insumo encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<EntityModel<ApiResponse<Inventario>>> obtener(@PathVariable Integer id) {
        ApiResponse<Inventario> response = ApiResponse.<Inventario>builder()
                .success(true)
                .message("Insumo encontrado")
                .data(servicio.obtener(id))
                .error(null)
                .build();

        EntityModel<ApiResponse<Inventario>> recurso = EntityModel.of(response);
        recurso.add(linkTo(methodOn(InventarioController.class).obtener(id)).withSelfRel());
        recurso.add(linkTo(methodOn(InventarioController.class).listar()).withRel("all"));
        recurso.add(linkTo(methodOn(InventarioController.class).actualizar(id, null)).withRel("update"));
        recurso.add(linkTo(methodOn(InventarioController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(recurso);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(summary = "Crear un insumo", description = "Agrega un nuevo insumo al inventario")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Insumo creado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<Inventario>> guardar(@Valid @RequestBody InventarioDTO dto) {
        log.info("Guardando insumo");
        return ResponseEntity.status(201).body(
                ApiResponse.<Inventario>builder()
                        .success(true)
                        .message("Insumo creado correctamente")
                        .data(servicio.guardar(dto))
                        .error(null)
                        .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(summary = "Actualizar un insumo", description = "Actualiza los datos de un insumo existente en el inventario")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Insumo actualizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<ApiResponse<Inventario>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody InventarioDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.<Inventario>builder()
                        .success(true)
                        .message("Inventario actualizado")
                        .data(servicio.actualizar(id, dto))
                        .error(null)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar un insumo", description = "Elimina un insumo del inventario por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Insumo eliminado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Insumo no encontrado")
    })
    public ResponseEntity<ApiResponse<String>> eliminar(@PathVariable Integer id) {
        servicio.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Inventario eliminado")
                        .data("OK")
                        .error(null)
                        .build()
        );
    }

    @PutMapping("/descontar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(summary = "Descontar stock", description = "Descuenta una cantidad del stock de un insumo")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Stock actualizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Stock insuficiente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<Void>> descontarStock(
            @PathVariable Integer id,
            @RequestParam Integer cantidad) {
        servicio.descontarStock(id, cantidad);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Stock actualizado")
                        .error(null)
                        .build()
        );
    }
}
