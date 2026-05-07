package com.joyeriajoy.joyeria_back.controller;

import com.joyeriajoy.joyeria_back.dto.common.ApiResponse;
import com.joyeriajoy.joyeria_back.dto.producto.ProductoRequest;
import com.joyeriajoy.joyeria_back.dto.producto.ProductoResponse;
import com.joyeriajoy.joyeria_back.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> getAllProductos() {
        List<ProductoResponse> productos = productoService.getAllProductos();
        return ResponseEntity.ok(ApiResponse.success(productos));
    }

    @GetMapping("/activos")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> getProductosActivos() {
        List<ProductoResponse> productos = productoService.getProductosActivos();
        return ResponseEntity.ok(ApiResponse.success(productos));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<ProductoResponse>> getProductoById(@PathVariable Long id) {
        ProductoResponse producto = productoService.getProductoById(id);
        return ResponseEntity.ok(ApiResponse.success(producto));
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDEDOR')")
    public ResponseEntity<ApiResponse<List<ProductoResponse>>> buscarProductos(@RequestParam String nombre) {
        List<ProductoResponse> productos = productoService.buscarProductosPorNombre(nombre);
        return ResponseEntity.ok(ApiResponse.success(productos));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductoResponse>> createProducto(@Valid @RequestBody ProductoRequest request) {
        ProductoResponse producto = productoService.createProducto(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Producto creado exitosamente", producto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductoResponse>> updateProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequest request) {
        ProductoResponse producto = productoService.updateProducto(id, request);
        return ResponseEntity.ok(ApiResponse.success("Producto actualizado exitosamente", producto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.ok(ApiResponse.success("Producto desactivado exitosamente", null));
    }
}
