package com.example.Historial_Medico.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Historial_MedicoResponse {

    private Long id;
    private LocalDate fecha;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    
    // Si quieres incluir información básica del paciente/mascota
    private Long mascotaId;
    private String nombreMascota;
    
    // Si quieres incluir quién fue el veterinario que lo atendió
    private String nombreVeterinario;
}