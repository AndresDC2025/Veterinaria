package com.example.Veterinarios.dto;

import java.util.List;
import lombok.*;

@Data
@Builder
public class VeterinarioResponse {

    private Long id;
    private String nombre;
    private String especialidad;
    private String horario;
    private String email;

    private List<ResenaResponse> resenas;
}