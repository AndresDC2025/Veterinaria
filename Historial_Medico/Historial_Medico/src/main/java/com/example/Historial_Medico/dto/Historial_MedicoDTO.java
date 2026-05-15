package com.example.Historial_Medico.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Historial_MedicoDTO {

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnostico;

    private String tratamiento;

    private String descripcion;

    @NotNull(message = "El ID de mascota es obligatorio")
    private Long idMascota;
}