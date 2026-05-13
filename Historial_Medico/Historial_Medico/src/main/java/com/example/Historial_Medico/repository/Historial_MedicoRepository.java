package com.example.Historial_Medico.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Historial_Medico.model.Historial_Medico;

import jakarta.transaction.Transactional;

public interface Historial_MedicoRepository extends JpaRepository<Historial_Medico, Long>{


    @Transactional
    void deleteById(Long id);

    Historial_Medico findById(long id);

    List<Historial_Medico> findByNombre(String nombre);

}
