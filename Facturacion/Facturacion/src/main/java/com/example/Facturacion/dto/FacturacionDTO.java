package com.example.Facturacion.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FacturacionDTO {

    @NotBlank(message = "El monto es obligatorio")
    private String monto;

    @NotBlank(message = "El metodo de pago es obligatorio")
    private String metodoP;

    public Integer getUsuarioId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUsuarioId'");
    }

	public Object getDetalle() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getDetalle'");
	}

}