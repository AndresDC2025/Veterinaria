package com.example.mascotas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import com.example.mascotas.dto.MascotaResponseDTO;
import com.example.mascotas.model.Mascota;
import com.example.mascotas.repository.MascotaRepository;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository repository;


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