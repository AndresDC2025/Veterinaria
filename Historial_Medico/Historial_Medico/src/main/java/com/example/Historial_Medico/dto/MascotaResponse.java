package com.example.Historial_Medico.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MascotaResponse {
    private Integer id;
    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;

}