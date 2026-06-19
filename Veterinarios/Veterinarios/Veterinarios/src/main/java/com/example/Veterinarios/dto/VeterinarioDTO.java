package com.example.Veterinarios.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VeterinarioDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @NotBlank(message = "El horario es obligatorio")
    private String horario;

    @NotBlank(message = "El email es obligatorio")
    private String email;
}