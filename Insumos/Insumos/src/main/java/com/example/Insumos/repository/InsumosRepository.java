package com.example.Insumos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Insumos.model.Insumos;

public interface InsumosRepository
        extends JpaRepository<Insumos, Long> {

    List<Insumos> findByNombre(String nombre);

    List<Insumos> findByProveedor(String proveedor);
}