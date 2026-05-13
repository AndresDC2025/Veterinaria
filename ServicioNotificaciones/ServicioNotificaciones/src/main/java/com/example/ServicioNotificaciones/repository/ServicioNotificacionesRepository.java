package com.example.ServicioNotificaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ServicioNotificaciones.model.ServicioNotificaciones;

import jakarta.transaction.Transactional;

public interface ServicioNotificacionesRepository extends JpaRepository<ServicioNotificaciones, Long>{


    @Transactional
    void deleteById(Long id);

    ServicioNotificaciones findById(long id);

    List<ServicioNotificaciones> findByNombre(String nombre);

}
