package com.example.Veterinarios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Veterinarios.model.Veterinarios;


public interface VeterinariosRepository  extends JpaRepository<Veterinarios, Long>{

    // Método para eliminar un usuario por su RUT

    // Método para encontrar un usuario por su Especialidad
    List<Veterinarios> findByEspecialidad(String especialidad);

    // Método para encontrar un usuario  por su nombre
    List<Veterinarios> findByNombre(String nombre);

}
