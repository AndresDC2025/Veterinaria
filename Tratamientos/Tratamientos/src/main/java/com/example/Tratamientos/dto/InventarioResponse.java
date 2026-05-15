package com.example.Tratamientos.dto;

import lombok.*;

@Builder
@Data
public class InventarioResponse {

    private Long id;
    private String nombre;
    private Integer stock;
    
}