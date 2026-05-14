package com.example.Cita.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CitaDTO {

    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;

    @NotBlank(message = "La hora es obligatoria")
    private String hora;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    // Estos IDs existen físicamente en otras bases de datos/microservicios
    @NotNull(message = "Debe indicar el ID del usuario")
    private Integer usuarioId;

    @NotNull(message = "Debe indicar el ID de la mascota")
    private Integer mascotaId;
}