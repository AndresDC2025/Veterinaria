package com.example.Tratamientos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tratamiento")
public class Tratamientos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String dosis;

    private String duracion;

    private Integer idHistorial;

    private Long inventarioId;
}