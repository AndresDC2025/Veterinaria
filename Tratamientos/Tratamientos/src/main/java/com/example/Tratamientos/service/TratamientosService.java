package com.example.Tratamientos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.Tratamientos.model.Tratamientos;
import com.example.Tratamientos.repository.TratamientosRepository;

@Service
public class TratamientosService {

    @Autowired
    private TratamientosRepository repository;


    public Tratamientos getById(Long id){
        return repository.findById(id).get();
    }

    public Tratamientos save(Tratamientos Tratamientos){
        return repository.save(Tratamientos);
    }

}