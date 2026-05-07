package com.joyeriajoy.joyeria_back.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoStockRequest {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long idProducto;

    private Long idRevendedor; // NULL = stock del dueño

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    private String tipoMovimiento; // ENTRADA, SALIDA, TRANSFERENCIA
}
