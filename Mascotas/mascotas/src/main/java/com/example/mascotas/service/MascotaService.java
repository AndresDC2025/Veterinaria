package com.example.mascotas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.mascotas.model.Mascota;
import com.example.mascotas.repository.MascotaRepository;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository repository;


    public Mascota getById(Long id){
        return repository.findById(id).get();
    }

   

    


}