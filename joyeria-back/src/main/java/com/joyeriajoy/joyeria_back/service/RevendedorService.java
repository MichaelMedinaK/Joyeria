package com.joyeriajoy.joyeria_back.service;

import com.joyeriajoy.joyeria_back.dto.revendedor.RevendedorRequest;
import com.joyeriajoy.joyeria_back.dto.revendedor.RevendedorResponse;
import com.joyeriajoy.joyeria_back.exception.ResourceNotFoundException;
import com.joyeriajoy.joyeria_back.mapper.RevendedorMapper;
import com.joyeriajoy.joyeria_back.model.entity.Revendedor;
import com.joyeriajoy.joyeria_back.repository.RevendedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RevendedorService {

    private final RevendedorRepository revendedorRepository;
    private final RevendedorMapper revendedorMapper;

    @Transactional(readOnly = true)
    public List<RevendedorResponse> getAllRevendedores() {
        log.info("Obteniendo todos los revendedores");
        return revendedorRepository.findAll().stream()
                .map(revendedorMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RevendedorResponse> getRevendedoresActivos() {
        log.info("Obteniendo revendedores activos");
        return revendedorRepository.findByActivoTrue().stream()
                .map(revendedorMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RevendedorResponse getRevendedorById(Long id) {
        log.info("Buscando revendedor con ID: {}", id);
        Revendedor revendedor = revendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Revendedor", "id", id));
        return revendedorMapper.toResponse(revendedor);
    }

    @Transactional
    public RevendedorResponse createRevendedor(RevendedorRequest request) {
        log.info("Creando nuevo revendedor: {}", request.getNombre());
        Revendedor revendedor = revendedorMapper.toEntity(request);
        if (revendedor.getActivo() == null) {
            revendedor.setActivo(true);
        }
        revendedor = revendedorRepository.save(revendedor);
        log.info("Revendedor creado con ID: {}", revendedor.getIdRevendedor());
        return revendedorMapper.toResponse(revendedor);
    }

    @Transactional
    public RevendedorResponse updateRevendedor(Long id, RevendedorRequest request) {
        log.info("Actualizando revendedor con ID: {}", id);
        Revendedor revendedor = revendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Revendedor", "id", id));
        
        revendedorMapper.updateEntityFromRequest(request, revendedor);
        revendedor = revendedorRepository.save(revendedor);
        log.info("Revendedor actualizado: {}", id);
        return revendedorMapper.toResponse(revendedor);
    }

    @Transactional
    public void deleteRevendedor(Long id) {
        log.info("Desactivando revendedor con ID: {}", id);
        Revendedor revendedor = revendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Revendedor", "id", id));
        revendedor.setActivo(false);
        revendedorRepository.save(revendedor);
        log.info("Revendedor desactivado: {}", id);
    }
}
