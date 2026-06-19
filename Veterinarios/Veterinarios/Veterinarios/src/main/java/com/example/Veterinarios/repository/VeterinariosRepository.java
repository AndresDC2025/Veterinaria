package com.example.Veterinarios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Veterinarios.model.Veterinarios;

public interface VeterinariosRepository
        extends JpaRepository<Veterinarios, Long> {

    List<Veterinarios> findByNombre(String nombre);

    List<Veterinarios> findByEspecialidad(String especialidad);
}