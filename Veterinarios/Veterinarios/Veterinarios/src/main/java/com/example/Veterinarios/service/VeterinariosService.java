package com.example.Veterinarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import com.example.Veterinarios.dto.VeterinariosResponseDTO;
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

    public List<VeterinariosResponseDTO> getAll() {
        List<VeterinariosResponseDTO> listaMascotas = new ArrayList<>();

        for(Veterinarios v: repository.findAll()){
            VeterinariosResponseDTO Veterinario = new VeterinariosResponseDTO();
            Veterinario.setId(v.getId());
            Veterinario.setNombre(v.getNombre());
            Veterinario.setEspecialidad(v.getEspecialidad());
            Veterinario.setEmail(v.getEmail());
            Veterinario.setHorario(v.getHorario());

            listaMascotas.add(Veterinario);
        }
        
        return listaMascotas;
    }
}
