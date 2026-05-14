package com.example.Facturacion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table
public class Facturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String monto;
    private String metodoP;
    private boolean estado;
    private Integer idCita;
    public void setUsuarioId(Integer usuarioId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUsuarioId'");
    }
    public void setDetalle(Object detalle) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDetalle'");
    }

}