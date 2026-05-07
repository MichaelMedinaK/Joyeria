package com.joyeriajoy.joyeria_back.mapper;

import com.joyeriajoy.joyeria_back.dto.cliente.ClienteRequest;
import com.joyeriajoy.joyeria_back.dto.cliente.ClienteResponse;
import com.joyeriajoy.joyeria_back.model.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteResponse toResponse(Cliente cliente);

    @Mapping(target = "idCliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    Cliente toEntity(ClienteRequest request);

    @Mapping(target = "idCliente", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    void updateEntityFromRequest(ClienteRequest request, @MappingTarget Cliente cliente);
}
