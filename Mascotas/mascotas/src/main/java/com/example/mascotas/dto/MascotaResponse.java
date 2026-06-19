package com.example.mascotas.dto;

import java.util.List;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MascotaResponse {

    private Integer id;
    private String nombre;
    private String raza;
    private Integer edad;

    private List<TratamientoResponse> tratamientos;
}