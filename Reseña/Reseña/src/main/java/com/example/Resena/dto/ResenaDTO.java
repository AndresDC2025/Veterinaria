package com.example.Resena.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ResenaDTO {

    @NotBlank(message = "La opinion es obligatoria")
    private String opinion;

    @NotNull(message = "Las estrellas son obligatoria")
    @Min(value = 0, message = "La cantidad de estrellas debe ser almenos 1. ")
    @Max(value = 5, message = "La cantidad de estrellas debe ser como maximo 5. ")
    private Integer estrellas;

}
