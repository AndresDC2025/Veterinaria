package com.example.Historial_Medico.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="historial_medico")
public class Historial_Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String diagnostico;
    private String tratamiento;
    private String descripcion;
    private Integer idMascota;
    private LocalDateTime fecha;
}