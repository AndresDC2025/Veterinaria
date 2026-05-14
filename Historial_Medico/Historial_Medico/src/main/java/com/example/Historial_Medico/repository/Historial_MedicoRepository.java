package com.example.Historial_Medico.repository;

import com.example.Historial_Medico.model.Historial_Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Historial_Medico.
 * Gestiona las operaciones de persistencia para los registros clínicos de las mascotas.
 */
@Repository
public interface Historial_MedicoRepository extends JpaRepository<Historial_Medico, Integer> {
    // Hereda métodos automáticos como save(), findById(), findAll() y deleteById().
}