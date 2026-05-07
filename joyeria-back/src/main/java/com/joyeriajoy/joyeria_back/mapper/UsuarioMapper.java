package com.joyeriajoy.joyeria_back.mapper;

import com.joyeriajoy.joyeria_back.dto.usuario.UsuarioResponse;
import com.joyeriajoy.joyeria_back.model.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "rol", source = "rol.codigo")
    UsuarioResponse toResponse(Usuario usuario);
}
