package com.example.Historial_Medico.service;

import com.example.Historial_Medico.dto.*;
import com.example.Historial_Medico.model.Historial_Medico;
import com.example.Historial_Medico.repository.Historial_MedicoRepository;
import com.example.Historial_Medico.client.MascotaClient;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class Historial_MedicoService {

    private final Historial_MedicoRepository repo;
    private final MascotaClient mascotaClient;

    // =========================
    // CREATE
    // =========================
    public Historial_MedicoResponse crear(Historial_MedicoDTO dto, String token) {

        log.info("Creando historial para mascota {}", dto.getIdMascota());

        var mascota = mascotaClient.obtenerMascota(dto.getIdMascota(), token);

        if (mascota == null) {
            throw new RuntimeException("La mascota con ID " + dto.getIdMascota() + " no existe.");
        }

        Historial_Medico entidad = new Historial_Medico();
        entidad.setDiagnostico(dto.getDiagnostico());
        entidad.setTratamiento(dto.getTratamiento());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setIdMascota(dto.getIdMascota().intValue());

        Historial_Medico guardado = repo.save(entidad);

        return mapToResponse(guardado, token);
    }

    // =========================
    // LISTAR
    // =========================
    public List<Historial_MedicoResponse> listar(String token) {

        return repo.findAll()
                .stream()
                .map(h -> mapToResponse(h, token))
                .toList();
    }

    // =========================
    // OBTENER
    // =========================
    public Historial_MedicoResponse obtener(Long id, String token) {

        Historial_Medico entidad = repo.findById(id.intValue())
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));

        return mapToResponse(entidad, token);
    }

    // =========================
    // ACTUALIZAR
    // =========================
    public Historial_MedicoResponse actualizar(Long id, Historial_MedicoDTO dto, String token) {

        Historial_Medico existente = repo.findById(id.intValue())
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));

        var mascota = mascotaClient.obtenerMascota(dto.getIdMascota(), token);

        if (mascota == null) {
            throw new RuntimeException("Mascota no encontrada");
        }

        existente.setDiagnostico(dto.getDiagnostico());
        existente.setTratamiento(dto.getTratamiento());
        existente.setDescripcion(dto.getDescripcion());
        existente.setIdMascota(dto.getIdMascota().intValue());

        return mapToResponse(repo.save(existente), token);
    }

    // =========================
    // ELIMINAR
    // =========================
    public void eliminar(Long id) {
        repo.deleteById(id.intValue());
    }

    // =========================
    // MAPPER (AQUÍ ESTÁ EL CAMBIO IMPORTANTE)
    // =========================
    private Historial_MedicoResponse mapToResponse(Historial_Medico entidad, String token) {

        var mascota = mascotaClient.obtenerMascota(entidad.getIdMascota().longValue(), token);

        return Historial_MedicoResponse.builder()
                .id(entidad.getId().intValue())
                .diagnostico(entidad.getDiagnostico())
                .tratamiento(entidad.getTratamiento())
                .descripcion(entidad.getDescripcion())
                .idmascota(entidad.getIdMascota().intValue())
                .mascota(mascota)   // 🔥 AQUÍ ES LA CLAVE (igual que tu UsuarioService)
                .build();
    }
}