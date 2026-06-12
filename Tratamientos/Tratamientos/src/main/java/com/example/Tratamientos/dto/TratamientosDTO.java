package com.example.Tratamientos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TratamientosDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La dosis es obligatoria")
    private String dosis;

    @NotBlank(message = "La duración es obligatoria")
    private String duracion;

    @NotNull(message = "El ID del historial es obligatorio")
    private Integer idHistorial;

    @NotNull(message = "El ID del inventario es obligatorio")
    private Long inventarioId;
}