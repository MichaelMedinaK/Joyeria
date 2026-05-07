package com.joyeriajoy.joyeria_back.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalleResponse {

    private Long idPedidoDetalle;
    private Long idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal precioCompraUnitario;
    private BigDecimal subtotal;
    private BigDecimal ganancia;
}
