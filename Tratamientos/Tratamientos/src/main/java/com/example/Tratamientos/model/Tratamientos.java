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

<<<<<<< HEAD
    private Integer idHistorial;

    // ID del producto del inventario asociado
    private Long inventarioId;
=======
    private Long mascotaId;
>>>>>>> aa98e270dfe1e4235839e43a47b993446df29bb7
}