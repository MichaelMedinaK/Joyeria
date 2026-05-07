package com.joyeriajoy.joyeria_back.mapper;

import com.joyeriajoy.joyeria_back.dto.stock.StockResponse;
import com.joyeriajoy.joyeria_back.model.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {

    @Mapping(target = "idProducto", source = "producto.idProducto")
    @Mapping(target = "nombreProducto", source = "producto.nombre")
    @Mapping(target = "idRevendedor", source = "revendedor.idRevendedor")
    @Mapping(target = "nombreRevendedor", source = "revendedor.nombre")
    @Mapping(target = "stockBajo", expression = "java(stock.getCantidadActual() <= stock.getStockMinimo())")
    StockResponse toResponse(Stock stock);
}
