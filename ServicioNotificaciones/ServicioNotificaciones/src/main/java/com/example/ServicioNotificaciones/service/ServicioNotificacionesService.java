package com.example.ServicioNotificaciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.ServicioNotificaciones.model.ServicioNotificaciones;
import com.example.ServicioNotificaciones.repository.ServicioNotificacionesRepository;

@Service
public class ServicioNotificacionesService {

    @Autowired
    private ServicioNotificacionesRepository repository;


    public ServicioNotificaciones getById(Long id){
        return repository.findById(id).get();
    }

    public ServicioNotificaciones save(ServicioNotificaciones ServicioNotificaciones){
        return repository.save(ServicioNotificaciones);
    }

}