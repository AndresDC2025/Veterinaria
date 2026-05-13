package com.example.Inventario.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Inventario.model.Inventario;

import jakarta.transaction.Transactional;

public interface InventarioRepository extends JpaRepository<Inventario, Long>{


    @Transactional
    void deleteById(Long id);

    Inventario findById(long id);

    List<Inventario> findByNombre(String nombre);

}
