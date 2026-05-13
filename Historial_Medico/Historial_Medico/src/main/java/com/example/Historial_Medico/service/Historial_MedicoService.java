package com.example.Historial_Medico.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.Historial_Medico.model.Historial_Medico;
import com.example.Historial_Medico.repository.Historial_MedicoRepository;

@Service
public class Historial_MedicoService {

    @Autowired
    private Historial_MedicoRepository repository;


    public Historial_Medico getById(Long id){
        return repository.findById(id).get();
    }

    public Historial_Medico save(Historial_Medico Historial_Medico){
        return repository.save(Historial_Medico);
    }

}