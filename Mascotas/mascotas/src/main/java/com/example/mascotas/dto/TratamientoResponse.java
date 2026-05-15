package com.example.mascotas.dto;

import lombok.Data;

@Data
public class TratamientoResponse {

    private Long id;
    private String nombre;
    private String dosis;
    private String duracion;
}