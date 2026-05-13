package com.example.Historial_Medico.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Historial_MedicoDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La raza es obligatoria")
    private String raza;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad debe ser positiva")
    private int edad;

}