package com.example.Inventario.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class InventarioDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotBlank(message = "El proveedor es obligatorio")
    private String proveedor;

    @NotNull(message = "El ID del insumo es obligatorio")
    private Integer insumoId;
}