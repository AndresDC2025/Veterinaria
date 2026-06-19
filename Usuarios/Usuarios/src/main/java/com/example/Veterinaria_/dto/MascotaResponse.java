package com.example.Veterinaria_.dto;

import lombok.Data;

@Data
public class MascotaResponse {

    private Integer id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    
}
