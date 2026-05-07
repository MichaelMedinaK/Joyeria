package com.joyeriajoy.joyeria_back.service;

import com.joyeriajoy.joyeria_back.dto.stock.MovimientoStockRequest;
import com.joyeriajoy.joyeria_back.dto.stock.StockRequest;
import com.joyeriajoy.joyeria_back.dto.stock.StockResponse;
import com.joyeriajoy.joyeria_back.exception.BadRequestException;
import com.joyeriajoy.joyeria_back.exception.ResourceNotFoundException;
import com.joyeriajoy.joyeria_back.exception.StockInsuficienteException;
import com.joyeriajoy.joyeria_back.mapper.StockMapper;
import com.joyeriajoy.joyeria_back.model.entity.Producto;
import com.joyeriajoy.joyeria_back.model.entity.Revendedor;
import com.joyeriajoy.joyeria_back.model.entity.Stock;
import com.joyeriajoy.joyeria_back.repository.ProductoRepository;
import com.joyeriajoy.joyeria_back.repository.RevendedorRepository;
import com.joyeriajoy.joyeria_back.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final StockRepository stockRepository;
    private final ProductoRepository productoRepository;
    private final RevendedorRepository revendedorRepository;
    private final StockMapper stockMapper;

    @Transactional(readOnly = true)
    public List<StockResponse> getStockDueno() {
        log.info("Obteniendo stock del dueño");
        return stockRepository.findAllStockDueno().stream()
                .map(stockMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StockResponse> getStockRevendedor(Long idRevendedor) {
        log.info("Obteniendo stock del revendedor ID: {}", idRevendedor);
        return stockRepository.findByRevendedorIdRevendedor(idRevendedor).stream()
                .map(stockMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StockResponse> getStockBajo() {
        log.info("Obteniendo productos con stock bajo");
        return stockRepository.findStockBajoDueno().stream()
                .map(stockMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StockResponse getStockByProductoAndRevendedor(Long idProducto, Long idRevendedor) {
        log.info("Consultando stock - Producto: {}, Revendedor: {}", idProducto, idRevendedor);
        
        Stock stock;
        if (idRevendedor == null) {
            stock = stockRepository.findStockDuenoPorProducto(idProducto)
                    .orElseThrow(() -> new ResourceNotFoundException("Stock del dueño no encontrado para producto: " + idProducto));
        } else {
            stock = stockRepository.findStockRevendedorPorProducto(idProducto, idRevendedor)
                    .orElseThrow(() -> new ResourceNotFoundException("Stock no encontrado para producto: " + idProducto + " y revendedor: " + idRevendedor));
        }
        
        return stockMapper.toResponse(stock);
    }

    @Transactional
    public StockResponse createOrUpdateStock(StockRequest request) {
        log.info("Creando/actualizando stock - Producto: {}, Revendedor: {}", 
                request.getIdProducto(), request.getIdRevendedor());

        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", request.getIdProducto()));

        Revendedor revendedor = null;
        if (request.getIdRevendedor() != null) {
            revendedor = revendedorRepository.findById(request.getIdRevendedor())
                    .orElseThrow(() -> new ResourceNotFoundException("Revendedor", "id", request.getIdRevendedor()));
        }

        // Buscar stock existente
        Stock stock;
        if (request.getIdRevendedor() == null) {
            stock = stockRepository.findStockDuenoPorProducto(request.getIdProducto())
                    .orElse(new Stock());
        } else {
            stock = stockRepository.findStockRevendedorPorProducto(request.getIdProducto(), request.getIdRevendedor())
                    .orElse(new Stock());
        }

        stock.setProducto(producto);
        stock.setRevendedor(revendedor);
        stock.setCantidadActual(request.getCantidadActual());
        stock.setStockMinimo(request.getStockMinimo() != null ? request.getStockMinimo() : 0);

        stock = stockRepository.save(stock);
        log.info("Stock guardado con ID: {}", stock.getIdStock());
        
        return stockMapper.toResponse(stock);
    }

    @Transactional
    public StockResponse procesarMovimiento(MovimientoStockRequest request) {
        log.info("Procesando movimiento de stock - Tipo: {}, Producto: {}", 
                request.getTipoMovimiento(), request.getIdProducto());

        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", request.getIdProducto()));

        Stock stock;
        if (request.getIdRevendedor() == null) {
            stock = stockRepository.findStockDuenoPorProducto(request.getIdProducto())
                    .orElseGet(() -> crearStockInicial(producto, null));
        } else {
            Revendedor revendedor = revendedorRepository.findById(request.getIdRevendedor())
                    .orElseThrow(() -> new ResourceNotFoundException("Revendedor", "id", request.getIdRevendedor()));
            stock = stockRepository.findStockRevendedorPorProducto(request.getIdProducto(), request.getIdRevendedor())
                    .orElseGet(() -> crearStockInicial(producto, revendedor));
        }

        switch (request.getTipoMovimiento().toUpperCase()) {
            case "ENTRADA":
                stock.setCantidadActual(stock.getCantidadActual() + request.getCantidad());
                log.info("Entrada de stock: +{}", request.getCantidad());
                break;
            case "SALIDA":
                if (stock.getCantidadActual() < request.getCantidad()) {
                    throw new StockInsuficienteException(
                            String.format("Stock insuficiente. Disponible: %d, Solicitado: %d", 
                                    stock.getCantidadActual(), request.getCantidad()));
                }
                stock.setCantidadActual(stock.getCantidadActual() - request.getCantidad());
                log.info("Salida de stock: -{}", request.getCantidad());
                break;
            default:
                throw new BadRequestException("Tipo de movimiento inválido: " + request.getTipoMovimiento());
        }

        stock = stockRepository.save(stock);
        return stockMapper.toResponse(stock);
    }

    @Transactional
    public void transferirStockARevendedor(Long idProducto, Long idRevendedor, Integer cantidad) {
        log.info("Transfiriendo stock - Producto: {}, Revendedor: {}, Cantidad: {}", 
                idProducto, idRevendedor, cantidad);

        // Descontar del stock del dueño
        Stock stockDueno = stockRepository.findStockDuenoPorProducto(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Stock del dueño no encontrado"));

        if (stockDueno.getCantidadActual() < cantidad) {
            throw new StockInsuficienteException("Stock insuficiente del dueño para transferir");
        }

        stockDueno.setCantidadActual(stockDueno.getCantidadActual() - cantidad);
        stockRepository.save(stockDueno);

        // Agregar al stock del revendedor
        Revendedor revendedor = revendedorRepository.findById(idRevendedor)
                .orElseThrow(() -> new ResourceNotFoundException("Revendedor", "id", idRevendedor));

        Stock stockRevendedor = stockRepository.findStockRevendedorPorProducto(idProducto, idRevendedor)
                .orElseGet(() -> crearStockInicial(stockDueno.getProducto(), revendedor));

        stockRevendedor.setCantidadActual(stockRevendedor.getCantidadActual() + cantidad);
        stockRepository.save(stockRevendedor);

        log.info("Transferencia completada exitosamente");
    }

    private Stock crearStockInicial(Producto producto, Revendedor revendedor) {
        Stock stock = Stock.builder()
                .producto(producto)
                .revendedor(revendedor)
                .cantidadActual(0)
                .stockMinimo(0)
                .build();
        return stockRepository.save(stock);
    }
}
