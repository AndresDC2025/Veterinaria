package com.example.Resena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Resena.model.Resena;

public interface ResenaRepository extends JpaRepository<Resena, Long> {

    List<Resena> findByEstrellas(Integer estrellas);


    List<Resena> findByVeterinarioId(Long veterinarioId);

}