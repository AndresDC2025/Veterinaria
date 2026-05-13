package com.example.Facturacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Facturacion.model.Facturacion;

import jakarta.transaction.Transactional;

public interface FacturacionRepository extends JpaRepository<Facturacion, Long>{


    @Transactional
    void deleteById(Long id);

    Facturacion findById(long id);

    List<Facturacion> findByNombre(String nombre);

}
