package com.example.mascotas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import com.example.mascotas.dto.MascotaResponseDTO;
import com.example.mascotas.model.Mascota;
import com.example.mascotas.repository.MascotaRepository;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository repository;

    // Esta es la solución que aplicaste en Usuario: el Bean dentro del Service
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    public List<MascotaResponseDTO> getAll() {
        List<MascotaResponseDTO> listaMascotas = new ArrayList<>();

        for(Mascota m: repository.findAll()){
            MascotaResponseDTO mascota = new MascotaResponseDTO();
            mascota.setId(m.getId());
            mascota.setNombre(m.getNombre());
            mascota.setRaza(m.getRaza());
            mascota.setEdad(m.getEdad());

            listaMascotas.add(mascota);
        }
        
        return listaMascotas;
    }


}