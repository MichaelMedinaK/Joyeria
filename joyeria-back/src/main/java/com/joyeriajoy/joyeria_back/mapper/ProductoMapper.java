package com.joyeriajoy.joyeria_back.mapper;

import com.joyeriajoy.joyeria_back.dto.producto.ProductoRequest;
import com.joyeriajoy.joyeria_back.dto.producto.ProductoResponse;
import com.joyeriajoy.joyeria_back.model.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoResponse toResponse(Producto producto);

    @Mapping(target = "idProducto", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    Producto toEntity(ProductoRequest request);

    @Mapping(target = "idProducto", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    void updateEntityFromRequest(ProductoRequest request, @MappingTarget Producto producto);
}
