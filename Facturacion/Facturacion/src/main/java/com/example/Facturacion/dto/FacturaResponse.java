package com.example.Facturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacturaResponse {
    
    private String monto;
    private String metodoP;
    private Integer idCita;
    private UsuarioResponse user;
}
