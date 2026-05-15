package com.example.Facturacion.dto;

import lombok.Data;

@Data
public class InventarioResponse {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer stock;
    private Double precio;
}