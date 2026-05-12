package com.example.Veterinaria_.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El rut es obligatorio")
    private String rut;

    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;

    @NotBlank(message = "El direccion es obligatorio")
    private String direccion;

    /* @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad debe ser positiva")
    private Integer id_mascota;
 */
    @NotNull(message = "El id_mascota es obligatorio")
    private Integer id_mascota;
}

