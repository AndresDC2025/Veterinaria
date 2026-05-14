package com.example.Resena.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.Resena.model.Resena;
import com.example.Resena.repository.ResenaRepository;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository repository;


    public Resena getById(Long id){
        return repository.findById(id).get();
    }

    public Resena save(Resena Resena){
        return repository.save(Resena);
    }

}