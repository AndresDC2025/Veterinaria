package com.example.Historial_Medico.repository;

import com.example.Historial_Medico.model.Historial_Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Historial_MedicoRepository extends JpaRepository<Historial_Medico, Integer> {
  
}