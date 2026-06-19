package com.example.Inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Inventario.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    List<Inventario> findByNombre(String nombre);

    List<Inventario> findByProveedor(String proveedor);

    boolean existsByNombre(String nombre);
}