package com.example.Veterinarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Veterinarios.model.Veterinarios;
import com.example.Veterinarios.repository.VeterinariosRepository;

@Service
public class VeterinariosService {
    
    @Autowired
    private VeterinariosRepository repository;

    // Esta es la solución que aplicaste en Usuario: el Bean dentro del Service
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    public Veterinarios getById(Long id){
        return repository.findById(id).get();
    }
    
    public Veterinarios getByName(String nombre){
        return repository.findByNombre(nombre).getString();
    }

    


}
