package com.example.Tratamientos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TratamientosDTO {

    @NotBlank@mesage = ("El nombre es obligatorio")
    private String nombre;

    @NotBlank
    @mesage = ("La dosis es obligatoria")
    private String dosis;

    @NotBlank
    @mesage = ("La duracion es obligatoria")
    private String duracion;

    @NotBlank
    @mesage = ("El ID del historial es obligatorio")
    private Integer idHistorial;
}