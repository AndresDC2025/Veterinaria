package com.example.Veterinarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeterinariosResponseDTO {

    private Long id;
    private String nombre;
    private String especialidad;
    private String horario;
    private String email;
}
