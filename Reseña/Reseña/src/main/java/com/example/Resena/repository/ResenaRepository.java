package com.example.Resena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Resena.model.Resena;

import jakarta.transaction.Transactional;

public interface ResenaRepository extends JpaRepository<Resena, Long>{


    @Transactional
    void deleteById(Long id);

    Resena findById(long id);

    List<Resena> findByNombre(String nombre);

}
