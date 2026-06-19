package com.example.Resena.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ResenaDTO {

    @NotBlank(message = "La opinión es obligatoria")
    private String opinion;

    @NotNull(message = "Las estrellas son obligatorias")
    @Min(1)
    @Max(5)
    private Integer estrellas;

    @NotNull(message = "El veterinario es obligatorio")
    private Long veterinarioId; 
}