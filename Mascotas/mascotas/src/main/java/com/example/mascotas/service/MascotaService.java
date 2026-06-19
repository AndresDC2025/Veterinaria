package com.example.mascotas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mascotas.client.TratamientoClient;
import com.example.mascotas.dto.MascotaDTO;
import com.example.mascotas.dto.MascotaResponse;
import com.example.mascotas.dto.TratamientoResponse;
import com.example.mascotas.model.Mascota;
import com.example.mascotas.repository.MascotaRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Service
@RequiredArgsConstructor
@Slf4j
public class MascotaService {

    private final MascotaRepository repository;
    private final TratamientoClient tratamientoClient;

    private final MascotaRepository repo;

    public Mascota crear(MascotaDTO dto) {
        log.info("Crear mascota", keyValue("nombre", dto.getNombre()));


        Mascota m = new Mascota();
        m.setNombre(dto.getNombre());
        m.setRaza(dto.getRaza());
        m.setEdad(dto.getEdad());
        
        return repo.save(m);
    }

    public List<Mascota> listar() {
        log.info("Listar mascotas");
        return repo.findAll();
    }

    public Mascota obtener(Long id) {
        log.info("Obtener mascota", keyValue("id", id));

        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada"));
    }

    public Mascota actualizar(Long id, MascotaDTO dto) {
        log.info("Actualizar mascota", keyValue("id", id));

        Mascota m = obtener(id);
        m.setNombre(dto.getNombre());
        m.setRaza(dto.getRaza());
        m.setEdad(dto.getEdad());

        return repo.save(m);
    }

    public void eliminar(Long id) {
        log.warn("Eliminar mascota", keyValue("id", id));
        repo.deleteById(id);
    }

    public MascotaResponse obtener(Long id, String token) {

    Mascota mascota = repository.findById(id)
            .orElseThrow();

    List<TratamientoResponse> tratamientos =
            tratamientoClient.listarPorMascota(id, token);

    return MascotaResponse.builder()
            .id(mascota.getId())
            .nombre(mascota.getNombre())
            .raza(mascota.getRaza())
            .edad(mascota.getEdad())
            .tratamientos(tratamientos)
            .build();
}
}