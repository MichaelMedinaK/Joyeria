package com.joyeriajoy.joyeria_back.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {

    private Long idPedido;
    private Long idCliente;
    private String nombreCliente;
    private Long idUsuario;
    private String nombreUsuario;
    private String estado;
    private BigDecimal kilometros;
    private BigDecimal subtotal;
    private BigDecimal costoDelivery;
    private BigDecimal total;
    private BigDecimal gananciaTotal;
    private LocalDateTime fechaPedido;
    private List<PedidoDetalleResponse> detalles;
}
