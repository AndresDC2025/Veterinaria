package com.example.mascotas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaResponseDTO {

    private Long id;
    private String nombre;
    private String raza;
    private int edad;

}
