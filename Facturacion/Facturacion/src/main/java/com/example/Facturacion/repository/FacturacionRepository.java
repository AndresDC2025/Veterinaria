package com.example.Facturacion.repository;

import com.example.Facturacion.model.Facturacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Facturacion.
 * Proporciona los métodos necesarios para la persistencia de datos en la tabla ms-facturacion.
 */
@Repository
public interface FacturacionRepository extends JpaRepository<Facturacion, Integer> {
    // JpaRepository ya incluye los métodos findAll(), findById(), save() y deleteById()
    // que utiliza tu FacturacionService.
}