package com.example.Tratamientos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TratamientosDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La dosis es obligatoria")
    private String dosis;

    @NotBlank(message = "La duración es obligatoria")
    private String duracion;

    @NotNull(message = "El historial es obligatorio")
    private Integer idHistorial;
}