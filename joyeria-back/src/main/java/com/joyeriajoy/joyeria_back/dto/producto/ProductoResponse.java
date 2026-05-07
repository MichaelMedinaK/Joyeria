package com.joyeriajoy.joyeria_back.dto.producto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {

    private Long idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
}
