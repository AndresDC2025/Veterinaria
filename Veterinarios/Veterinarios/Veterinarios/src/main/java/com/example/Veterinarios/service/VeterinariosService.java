package com.example.Veterinarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.Veterinarios.model.Veterinarios;
import com.example.Veterinarios.repository.VeterinariosRepository;


@Service
public class VeterinariosService {

    @Autowired
    private VeterinariosRepository repository;


    public Veterinarios getById(Long id){
        return repository.findById(id).get();
    }

    public Veterinarios save(Veterinarios veterinario){
        return repository.save(veterinario);
    }

}

