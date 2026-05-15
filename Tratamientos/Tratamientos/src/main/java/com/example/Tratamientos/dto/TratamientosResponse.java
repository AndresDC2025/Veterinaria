package com.example.Tratamientos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TratamientosResponse {

    private Long id;
    private String nombre;
    private String dosis;
    private String duracion;
    private Integer idHistorial;
    private InventarioResponse inventario;
}