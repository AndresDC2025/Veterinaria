package com.example.Facturacion.dto;


import lombok.Data;

@Data
public class CitaResponse {

    private Integer id;

    private String fecha;

    private String hora;

    private String estado;

    private Integer mascotaId;

    private Integer veterinarioId;
}