package com.example.Resena.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Resena.dto.ResenaDTO;
import com.example.Resena.model.Resena;
import com.example.Resena.repository.ResenaRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResenaService {

    private final ResenaRepository repository;

    public List<Resena> listar() {
        return repository.findAll();
    }

    public Resena getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña no encontrada"));
    }

    public Resena save(ResenaDTO dto) {

        log.info("Guardando reseña");

        Resena resena = Resena.builder()
                .opinion(dto.getOpinion())
                .estrellas(dto.getEstrellas())
                .veterinarioId(dto.getVeterinarioId())
                .build();

        return repository.save(resena);
    }

    public Resena actualizar(Long id, ResenaDTO dto) {

        Resena resena = getById(id);

        resena.setOpinion(dto.getOpinion());
        resena.setEstrellas(dto.getEstrellas());
        resena.setVeterinarioId(dto.getVeterinarioId());

        return repository.save(resena);
    }

    public void eliminar(Long id) {

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Reseña no encontrada");
        }

        repository.deleteById(id);
    }

    public List<Resena> listarPorVeterinario(Long veterinarioId) {
        return repository.findByVeterinarioId(veterinarioId);
    }
}