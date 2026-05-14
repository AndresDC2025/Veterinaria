package com.example.Facturacion.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FacturacionDTO {

    @NotBlank(message = "El monto es obligatorio")
    private String monto;

    @NotBlank(message = "El metodo de pago es obligatorio")
    private String metodoP;

    @NotNull(message = "El estado es obligatorio")
    private boolean estado;

    
    private Integer idCita;
}