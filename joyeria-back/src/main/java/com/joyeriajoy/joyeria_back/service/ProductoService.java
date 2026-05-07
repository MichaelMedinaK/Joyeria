package com.joyeriajoy.joyeria_back.service;

import com.joyeriajoy.joyeria_back.dto.producto.ProductoRequest;
import com.joyeriajoy.joyeria_back.dto.producto.ProductoResponse;
import com.joyeriajoy.joyeria_back.exception.ResourceNotFoundException;
import com.joyeriajoy.joyeria_back.mapper.ProductoMapper;
import com.joyeriajoy.joyeria_back.model.entity.Producto;
import com.joyeriajoy.joyeria_back.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Transactional(readOnly = true)
    public List<ProductoResponse> getAllProductos() {
        log.info("Obteniendo todos los productos");
        return productoRepository.findAll().stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> getProductosActivos() {
        log.info("Obteniendo productos activos");
        return productoRepository.findByActivoTrue().stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductoResponse getProductoById(Long id) {
        log.info("Buscando producto con ID: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
        return productoMapper.toResponse(producto);
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> buscarProductosPorNombre(String nombre) {
        log.info("Buscando productos por nombre: {}", nombre);
        return productoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductoResponse createProducto(ProductoRequest request) {
        log.info("Creando nuevo producto: {}", request.getNombre());
        Producto producto = productoMapper.toEntity(request);
        if (producto.getActivo() == null) {
            producto.setActivo(true);
        }
        producto = productoRepository.save(producto);
        log.info("Producto creado con ID: {}", producto.getIdProducto());
        return productoMapper.toResponse(producto);
    }

    @Transactional
    public ProductoResponse updateProducto(Long id, ProductoRequest request) {
        log.info("Actualizando producto con ID: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
        
        productoMapper.updateEntityFromRequest(request, producto);
        producto = productoRepository.save(producto);
        log.info("Producto actualizado: {}", id);
        return productoMapper.toResponse(producto);
    }

    @Transactional
    public void deleteProducto(Long id) {
        log.info("Desactivando producto con ID: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
        producto.setActivo(false);
        productoRepository.save(producto);
        log.info("Producto desactivado: {}", id);
    }
}
