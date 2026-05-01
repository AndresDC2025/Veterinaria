package com.example.Veterinaria_.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String rut;
    private String email;
    private String telefono;
    private String direccion;

}

