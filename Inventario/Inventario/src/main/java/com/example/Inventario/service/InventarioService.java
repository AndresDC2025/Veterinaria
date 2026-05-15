package com.example.Inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Inventario.dto.InventarioDTO;
import com.example.Inventario.model.Inventario;
import com.example.Inventario.repository.InventarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository repositorio;

    public Inventario guardar(InventarioDTO dto) {

        Inventario inventario = Inventario.builder()
                .nombre(dto.getNombre())
                .stock(dto.getStock())
                .proveedor(dto.getProveedor())
                .build();

        return repositorio.save(inventario);
    }

    public List<Inventario> listar() {

        return repositorio.findAll();
    }

    public Inventario obtener(Integer id) {

        return repositorio.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Inventario no encontrado"
                        ));
    }

    public Inventario actualizar(
            Integer id,
            InventarioDTO dto
    ) {

        Inventario inventario = repositorio.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Inventario no encontrado"
                        ));

        inventario.setNombre(dto.getNombre());
        inventario.setStock(dto.getStock());
        inventario.setProveedor(dto.getProveedor());

        return repositorio.save(inventario);
    }

    public void eliminar(Integer id) {

        if (!repositorio.existsById(id)) {

            throw new EntityNotFoundException(
                    "Inventario no encontrado"
            );
        }

        repositorio.deleteById(id);
    }

    public void descontarStock(Integer id, Integer cantidad) {

    Inventario inventario = repositorio.findById(id)
            .orElseThrow(() ->
                    new EntityNotFoundException(
                            "Insumo no encontrado"));

    if (inventario.getStock() < cantidad) {
        throw new RuntimeException(
                "Stock insuficiente");
    }

    inventario.setStock(
            inventario.getStock() - cantidad
    );

    repositorio.save(inventario);
}
}