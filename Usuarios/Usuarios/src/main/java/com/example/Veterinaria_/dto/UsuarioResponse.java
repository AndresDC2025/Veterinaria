package com.example.Veterinaria_.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String rut;
    private String email;
    private String telefono;
    private String direccion;
    private MascotaResponse mascota;
    private VeterinariosResponse veterinario;


}

