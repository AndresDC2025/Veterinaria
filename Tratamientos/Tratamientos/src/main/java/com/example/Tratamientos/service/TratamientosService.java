package com.example.Tratamientos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Tratamientos.dto.TratamientosDTO;
import com.example.Tratamientos.model.Tratamientos;
import com.example.Tratamientos.repository.TratamientosRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TratamientosService {

    private final TratamientosRepository repository;

    public List<Tratamientos> listar() {
        return repository.findAll();
    }

    public Tratamientos getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tratamiento no encontrado"));
    }

    public Tratamientos save(TratamientosDTO dto) {

        Tratamientos tratamiento = Tratamientos.builder()
                .nombre(dto.getNombre())
                .dosis(dto.getDosis())
                .duracion(dto.getDuracion())
                .mascotaId(dto.getMascotaId())
                .build();

        return repository.save(tratamiento);
    }

    public Tratamientos actualizar(Long id, TratamientosDTO dto) {

        Tratamientos tratamiento = getById(id);

        tratamiento.setNombre(dto.getNombre());
        tratamiento.setDosis(dto.getDosis());
        tratamiento.setDuracion(dto.getDuracion());
        tratamiento.setMascotaId(dto.getMascotaId());

        return repository.save(tratamiento);
    }

    public void eliminar(Long id) {

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Tratamiento no encontrado");
        }

        repository.deleteById(id);
    }

    public List<Tratamientos> listarPorMascota(Long mascotaId) {
        return repository.findByMascotaId(mascotaId);
    }
}