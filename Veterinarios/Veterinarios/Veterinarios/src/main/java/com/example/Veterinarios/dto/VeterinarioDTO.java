package com.example.Veterinarios.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VeterinarioDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La raza es obligatoria")
    private String raza;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad debe ser positiva")
    private int edad;

}