package com.example.Cita.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Cita.model.Cita;

import jakarta.transaction.Transactional;

    public interface CitaRepository extends JpaRepository<Cita, Long>{


    @Transactional
    void deleteById(Long id);

    
}
