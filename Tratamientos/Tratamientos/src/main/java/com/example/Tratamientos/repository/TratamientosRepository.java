package com.example.Tratamientos.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Tratamientos.model.Tratamientos;

import jakarta.transaction.Transactional;

public interface TratamientosRepository extends JpaRepository<Tratamientos, Long>{


    @Transactional
    void deleteById(Long id);

    Tratamientos findById(long id);

    List<Tratamientos> findByNombre(String nombre);

}
