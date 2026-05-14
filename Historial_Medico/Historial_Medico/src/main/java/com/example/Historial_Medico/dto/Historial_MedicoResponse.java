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

    private Integer id;
    private LocalDate fecha;
    private String diagnostico;
    private String tratamiento;
    private String descripcion;
    private Integer mascotaId;

}