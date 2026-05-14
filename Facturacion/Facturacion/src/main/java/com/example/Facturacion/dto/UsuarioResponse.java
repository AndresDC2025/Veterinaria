package com.example.Facturacion.dto;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Integer id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private Integer monto;
    private String metodoP;
    private boolean estado;
    private Integer idCita;
}