package com.example.Facturacion.service;

import com.example.Facturacion.dto.FacturacionDTO;
import com.example.Facturacion.model.Facturacion;
import com.example.Facturacion.repository.FacturacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturacionService {

    private final FacturacionRepository repository;

    public Facturacion crear(FacturacionDTO dto, String token) {
        Facturacion nuevaFactura = new Facturacion();
        nuevaFactura.setMonto(dto.getMonto());
        nuevaFactura.setMetodoP(dto.getMetodoP());

        return repository.save(nuevaFactura);
    }

    public List<Facturacion> listar() {
        return repository.findAll();
    }

    public Facturacion obtener(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con el ID: " + id));
    }

    public Facturacion actualizar(Integer id, FacturacionDTO dto, String token) {

        Facturacion facturaExistente = obtener(id);

        facturaExistente.setMonto(dto.getMonto());
        facturaExistente.setMetodoP(dto.getMetodoP());

        return repository.save(facturaExistente);
    }

    public void eliminar(Integer id) {
        Facturacion facturaExistente = obtener(id);
        repository.delete(facturaExistente);
    }
}