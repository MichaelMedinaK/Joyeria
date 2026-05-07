package com.joyeriajoy.joyeria_back.dto.pedido;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class PedidoRequest {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El estado es obligatorio")
    private String estado; // PENDIENTE, EN_PROCESO, EN_CAMINO, ENTREGADO, CANCELADO

    @NotNull(message = "Los kilómetros son obligatorios")
    @DecimalMin(value = "0.0", inclusive = true, message = "Los kilómetros no pueden ser negativos")
    private BigDecimal kilometros;

    @Valid
    @NotNull(message = "Los detalles del pedido son obligatorios")
    private List<PedidoDetalleRequest> detalles;
}
