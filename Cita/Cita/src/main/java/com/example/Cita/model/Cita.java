package com.example.Cita.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cita")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fecha;
    private String hora;
    private String motivo;

    private Integer usuarioId;
    private Integer MascotaId;
}