package com.example.Insumos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Insumos.model.Insumos;

import jakarta.transaction.Transactional;

public interface InsumosRepository extends JpaRepository<Insumos, Long>{


    @Transactional
    void deleteById(Long id);

    Insumos findById(long id);

    List<Insumos> findByNombre(String nombre);

}
