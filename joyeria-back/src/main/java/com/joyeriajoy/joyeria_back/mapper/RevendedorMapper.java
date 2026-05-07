package com.joyeriajoy.joyeria_back.mapper;

import com.joyeriajoy.joyeria_back.dto.revendedor.RevendedorRequest;
import com.joyeriajoy.joyeria_back.dto.revendedor.RevendedorResponse;
import com.joyeriajoy.joyeria_back.model.entity.Revendedor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RevendedorMapper {

    RevendedorResponse toResponse(Revendedor revendedor);

    @Mapping(target = "idRevendedor", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    Revendedor toEntity(RevendedorRequest request);

    @Mapping(target = "idRevendedor", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    void updateEntityFromRequest(RevendedorRequest request, @MappingTarget Revendedor revendedor);
}
