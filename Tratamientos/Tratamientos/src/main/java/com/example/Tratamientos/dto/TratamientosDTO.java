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

<<<<<<< HEAD
    @NotNull(message = "El historial es obligatorio")
    private Integer idHistorial;

    @NotNull(message = "El inventario es obligatorio")
    private Long inventarioId;
=======
    @NotNull(message = "La mascota es obligatoria")
    private Long mascotaId;
>>>>>>> aa98e270dfe1e4235839e43a47b993446df29bb7
}