package com.example.Facturacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.Facturacion.model.Facturacion;
import com.example.Facturacion.repository.FacturacionRepository;

@Service
public class FacturacionService {

    @Autowired
    private FacturacionRepository repository;


    public Facturacion getById(Long id){
        return repository.findById(id).get();
    }

    public Facturacion save(Facturacion facturacion){
        return repository.save(facturacion);
    }
   

    


}