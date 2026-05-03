package com.example.Veterinaria_.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Veterinaria_.model.Usuario;

import jakarta.transaction.Transactional;



public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    // Método para eliminar un usuario por su RUT
    @Transactional
    void deleteByRut(String rut);

    // Método para encontrar un usuario por su RUT
    Usuario findByRut(String rut);

    // Método para encontrar un usuario  por su nombre
    List<Usuario> findByNombre(String nombre);


}
