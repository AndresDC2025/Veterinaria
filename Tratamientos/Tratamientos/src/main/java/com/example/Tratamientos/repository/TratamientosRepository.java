package com.example.Tratamientos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Tratamientos.model.Tratamientos;

public interface TratamientosRepository extends JpaRepository<Tratamientos, Long> {

    List<Tratamientos> findByNombre(String nombre);

    List<Tratamientos> findByMascotaId(Long mascotaId);
}