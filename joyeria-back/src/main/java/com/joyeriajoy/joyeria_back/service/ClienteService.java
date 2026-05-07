package com.joyeriajoy.joyeria_back.service;

import com.joyeriajoy.joyeria_back.dto.cliente.ClienteRequest;
import com.joyeriajoy.joyeria_back.dto.cliente.ClienteResponse;
import com.joyeriajoy.joyeria_back.exception.ResourceNotFoundException;
import com.joyeriajoy.joyeria_back.mapper.ClienteMapper;
import com.joyeriajoy.joyeria_back.model.entity.Cliente;
import com.joyeriajoy.joyeria_back.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Transactional(readOnly = true)
    public List<ClienteResponse> getAllClientes() {
        log.info("Obteniendo todos los clientes");
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteResponse getClienteById(Long id) {
        log.info("Buscando cliente con ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        return clienteMapper.toResponse(cliente);
    }

    @Transactional
    public ClienteResponse createCliente(ClienteRequest request) {
        log.info("Creando nuevo cliente: {}", request.getNombre());
        Cliente cliente = clienteMapper.toEntity(request);
        cliente = clienteRepository.save(cliente);
        log.info("Cliente creado con ID: {}", cliente.getIdCliente());
        return clienteMapper.toResponse(cliente);
    }

    @Transactional
    public ClienteResponse updateCliente(Long id, ClienteRequest request) {
        log.info("Actualizando cliente con ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        
        clienteMapper.updateEntityFromRequest(request, cliente);
        cliente = clienteRepository.save(cliente);
        log.info("Cliente actualizado: {}", id);
        return clienteMapper.toResponse(cliente);
    }

    @Transactional
    public void deleteCliente(Long id) {
        log.info("Eliminando cliente con ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        clienteRepository.delete(cliente);
        log.info("Cliente eliminado: {}", id);
    }
}
