package com.example.Insumos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class InsumosDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotBlank(message = "El proveedor es obligatorio")
    private String proveedor;
}