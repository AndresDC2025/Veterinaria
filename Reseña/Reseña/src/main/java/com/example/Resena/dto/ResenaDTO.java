package com.example.Resena.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ResenaDTO {

    @NotBlank(message = "La opinión es obligatoria")
    private String opinion;

    @NotNull(message = "Las estrellas son obligatorias")
    @Min(value = 1, message = "La cantidad mínima es 1")
    @Max(value = 5, message = "La cantidad máxima es 5")
    private Integer estrellas;
}