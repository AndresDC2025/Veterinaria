package com.example.mascotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mascotas.model.Mascota;

import jakarta.transaction.Transactional;

public interface MascotaRepository extends JpaRepository<Mascota, Long>{

    @Transactional
    void deleteById(Long id);

    Mascota findById(long id);

    List<Mascota> findByNombre(String nombre);

}
