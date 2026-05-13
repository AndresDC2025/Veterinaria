package com.example.Insumos.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.Insumos.model.Insumos;
import com.example.Insumos.repository.InsumosRepository;

@Service
public class InsumosService {

    @Autowired
    private InsumosRepository repository;


    public Insumos getById(Long id){
        return repository.findById(id).get();
    }

    public Insumos save(Insumos insumos){
        return repository.save(insumos);
    }

}