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
public class StockRequest {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long idProducto;

    private Long idRevendedor; // NULL = stock del dueño

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer cantidadActual;

    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimo;
}
