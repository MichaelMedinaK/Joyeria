package com.joyeriajoy.joyeria_back.mapper;

import com.joyeriajoy.joyeria_back.dto.pedido.PedidoDetalleResponse;
import com.joyeriajoy.joyeria_back.dto.pedido.PedidoResponse;
import com.joyeriajoy.joyeria_back.model.entity.Pedido;
import com.joyeriajoy.joyeria_back.model.entity.PedidoDetalle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "idCliente", source = "cliente.idCliente")
    @Mapping(target = "nombreCliente", source = "cliente.nombre")
    @Mapping(target = "idUsuario", source = "usuario.idUsuario")
    @Mapping(target = "nombreUsuario", source = "usuario.nombre")
    @Mapping(target = "estado", expression = "java(pedido.getEstado().name())")
    PedidoResponse toResponse(Pedido pedido);

    @Mapping(target = "idProducto", source = "producto.idProducto")
    @Mapping(target = "nombreProducto", source = "producto.nombre")
    PedidoDetalleResponse toDetalleResponse(PedidoDetalle detalle);

    List<PedidoDetalleResponse> toDetalleResponseList(List<PedidoDetalle> detalles);
}
