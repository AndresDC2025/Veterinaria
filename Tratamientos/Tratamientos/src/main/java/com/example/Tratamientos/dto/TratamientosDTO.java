package com.example.Tratamientos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TratamientosDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String dosis;

    @NotBlank
    private String duracion;

    @NotNull(message = "La mascota es obligatoria")
    private Long mascotaId;
}