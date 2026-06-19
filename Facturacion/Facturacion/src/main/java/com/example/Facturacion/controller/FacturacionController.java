package com.example.Facturacion.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Facturacion.dto.ApiResponse;
import com.example.Facturacion.dto.FacturacionDTO;
import com.example.Facturacion.model.Facturacion;
import com.example.Facturacion.service.FacturacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/facturacion")
@RequiredArgsConstructor
@Tag(name = "Facturación", description = "Operaciones relacionadas con la facturación")
public class FacturacionController {

    private final FacturacionService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear una factura", description = "Crea una nueva factura en el sistema")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Factura creada exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<Facturacion>> crear(
            @Valid @RequestBody FacturacionDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(201).body(
                ApiResponse.<Facturacion>builder()
                        .success(true)
                        .message("Factura creada correctamente")
                        .data(service.crear(dto, token))
                        .error(null)
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Listar todas las facturas", description = "Obtiene una lista de todas las facturas")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listado obtenido"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<ApiResponse<List<Facturacion>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.<List<Facturacion>>builder()
                        .success(true)
                        .message("Listado de facturas")
                        .data(service.listar())
                        .error(null)
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Obtener factura por ID", description = "Obtiene los detalles de una factura por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Factura encontrada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    public ResponseEntity<EntityModel<ApiResponse<Facturacion>>> obtener(@PathVariable Integer id) {
        ApiResponse<Facturacion> response = ApiResponse.<Facturacion>builder()
                .success(true)
                .message("Factura encontrada")
                .data(service.obtener(id))
                .error(null)
                .build();

        EntityModel<ApiResponse<Facturacion>> recurso = EntityModel.of(response);
        recurso.add(linkTo(methodOn(FacturacionController.class).obtener(id)).withSelfRel());
        recurso.add(linkTo(methodOn(FacturacionController.class).listar()).withRel("all"));
        recurso.add(linkTo(methodOn(FacturacionController.class).actualizar(id, null, null)).withRel("update"));
        recurso.add(linkTo(methodOn(FacturacionController.class).eliminar(id)).withRel("delete"));

        return ResponseEntity.ok(recurso);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar una factura", description = "Actualiza los datos de una factura existente")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Factura actualizada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    public ResponseEntity<ApiResponse<Facturacion>> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody FacturacionDTO dto,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                ApiResponse.<Facturacion>builder()
                        .success(true)
                        .message("Factura actualizada")
                        .data(service.actualizar(id, dto, token))
                        .error(null)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar una factura", description = "Elimina una factura del sistema por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Factura eliminada"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "No autorizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Factura eliminada")
                        .error(null)
                        .build()
        );
    }
}
