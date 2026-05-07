package com.joyeriajoy.joyeria_back.dto.pedido;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoUpdateRequest {

    // Todos los campos son opcionales - solo se actualizan los que se envíen
    
    private Long idCliente; // Para cambiar el cliente (y su dirección)
    
    private String estado; // PENDIENTE, EN_PROCESO, EN_CAMINO, ENTREGADO, CANCELADO
    
    @DecimalMin(value = "0.0", inclusive = true, message = "Los kilómetros no pueden ser negativos")
    private BigDecimal kilometros;
    
    private java.time.LocalDate fechaEntrega;
    
    private String rangoHorario;
    
    private String tipoPago; // EFECTIVO, TRANSFERENCIA, MIXTO
    
    @DecimalMin(value = "0.0", inclusive = true, message = "El monto en efectivo no puede ser negativo")
    private BigDecimal efectivo;
    
    @DecimalMin(value = "0.0", inclusive = true, message = "El monto en transferencia no puede ser negativo")
    private BigDecimal transferencia;
    
    @Valid
    private List<PedidoDetalleRequest> detalles; // Para actualizar productos y cantidades
}
