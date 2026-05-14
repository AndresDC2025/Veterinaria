package com.example.Insumos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Insumos.dto.InsumosDTO;
import com.example.Insumos.model.Insumos;
import com.example.Insumos.repository.InsumosRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InsumosService {

    private final InsumosRepository repository;

    public List<Insumos> listar() {

        return repository.findAll();
    }

    public Insumos getById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Insumo no encontrado"
                        ));
    }

    public Insumos save(InsumosDTO dto) {

        log.info("Guardando insumo");

        Insumos insumo = Insumos.builder()
                .nombre(dto.getNombre())
                .stock(dto.getStock())
                .proveedor(dto.getProveedor())
                .build();

        return repository.save(insumo);
    }

    public Insumos actualizar(
            Long id,
            InsumosDTO dto
    ) {

        Insumos insumo = getById(id);

        insumo.setNombre(dto.getNombre());
        insumo.setStock(dto.getStock());
        insumo.setProveedor(dto.getProveedor());

        return repository.save(insumo);
    }

    public void eliminar(Long id) {

        if (!repository.existsById(id)) {

            throw new EntityNotFoundException(
                    "Insumo no encontrado"
            );
        }

        repository.deleteById(id);
    }
}