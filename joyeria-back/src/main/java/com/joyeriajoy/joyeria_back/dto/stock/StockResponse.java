package com.joyeriajoy.joyeria_back.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {

    private Long idStock;
    private Long idProducto;
    private String nombreProducto;
    private Long idRevendedor;
    private String nombreRevendedor;
    private Integer cantidadActual;
    private Integer stockMinimo;
    private LocalDateTime fechaActualizacion;
    private Boolean stockBajo; // Calculado: cantidadActual <= stockMinimo
}
