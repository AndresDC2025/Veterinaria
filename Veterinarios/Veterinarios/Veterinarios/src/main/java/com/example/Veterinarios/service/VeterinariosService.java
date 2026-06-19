package com.example.Veterinarios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Veterinarios.client.ResenaClient;
import com.example.Veterinarios.dto.VeterinarioDTO;
import com.example.Veterinarios.dto.VeterinarioResponse;
import com.example.Veterinarios.model.Veterinarios;
import com.example.Veterinarios.repository.VeterinariosRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeterinariosService {

    private final VeterinariosRepository repositorio;
    private final ResenaClient resenaClient;

    public List<Veterinarios> listar() {
        return repositorio.findAll();
    }

    public Veterinarios obtenerEntity(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veterinario no encontrado"));
    }

    public VeterinarioResponse obtener(Long id, String token) {

        Veterinarios v = obtenerEntity(id);

        return VeterinarioResponse.builder()
                .id(v.getId())
                .nombre(v.getNombre())
                .especialidad(v.getEspecialidad())
                .horario(v.getHorario())
                .email(v.getEmail())
                .resenas(resenaClient.listarPorVeterinario(id, token))
                .build();
    }

    public Veterinarios guardar(VeterinarioDTO dto) {

        Veterinarios v = Veterinarios.builder()
                .nombre(dto.getNombre())
                .especialidad(dto.getEspecialidad())
                .horario(dto.getHorario())
                .email(dto.getEmail())
                .build();

        return repositorio.save(v);
    }

    public Veterinarios actualizar(Long id, VeterinarioDTO dto) {

        Veterinarios v = obtenerEntity(id);

        v.setNombre(dto.getNombre());
        v.setEspecialidad(dto.getEspecialidad());
        v.setHorario(dto.getHorario());
        v.setEmail(dto.getEmail());

        return repositorio.save(v);
    }

    public void eliminar(Long id) {

        if (!repositorio.existsById(id)) {
            throw new EntityNotFoundException("Veterinario no encontrado");
        }

        repositorio.deleteById(id);
    }
}