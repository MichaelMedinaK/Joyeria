package com.joyeriajoy.joyeria_back.service;

import com.joyeriajoy.joyeria_back.dto.pedido.PedidoDetalleRequest;
import com.joyeriajoy.joyeria_back.dto.pedido.PedidoRequest;
import com.joyeriajoy.joyeria_back.dto.pedido.PedidoResponse;
import com.joyeriajoy.joyeria_back.exception.ResourceNotFoundException;
import com.joyeriajoy.joyeria_back.exception.StockInsuficienteException;
import com.joyeriajoy.joyeria_back.mapper.PedidoMapper;
import com.joyeriajoy.joyeria_back.model.entity.*;
import com.joyeriajoy.joyeria_back.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final StockRepository stockRepository;
    private final PedidoMapper pedidoMapper;

    @Transactional(readOnly = true)
    public List<PedidoResponse> getAllPedidos() {
        log.info("Obteniendo todos los pedidos");
        return pedidoRepository.findAll().stream()
                .map(pedidoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PedidoResponse getPedidoById(Long id) {
        log.info("Buscando pedido con ID: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", "id", id));
        return pedidoMapper.toResponse(pedido);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> getPedidosByCliente(Long idCliente) {
        log.info("Obteniendo pedidos del cliente ID: {}", idCliente);
        return pedidoRepository.findByClienteIdCliente(idCliente).stream()
                .map(pedidoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> getPedidosByEstado(String estado) {
        log.info("Obteniendo pedidos con estado: {}", estado);
        Pedido.EstadoPedido estadoPedido = Pedido.EstadoPedido.valueOf(estado.toUpperCase());
        return pedidoRepository.findByEstado(estadoPedido).stream()
                .map(pedidoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Double getGananciaDiaria() {
        log.info("Calculando ganancia diaria");
        Double ganancia = pedidoRepository.calcularGananciaDiaria();
        return ganancia != null ? ganancia : 0.0;
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> getPedidosByFecha(LocalDate fecha) {
        log.info("Obteniendo pedidos de la fecha: {}", fecha);
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(LocalTime.MAX);
        return pedidoRepository.findByFechaPedidoBetween(inicio, fin).stream()
                .map(pedidoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoResponse createPedido(PedidoRequest request) {
        log.info("Creando nuevo pedido para cliente ID: {}", request.getIdCliente());

        // Validar cliente
        Cliente cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", request.getIdCliente()));

        // Validar usuario
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", request.getIdUsuario()));

        // Validar estado
        Pedido.EstadoPedido estado;
        try {
            estado = Pedido.EstadoPedido.valueOf(request.getEstado().toUpperCase());
        } catch (IllegalArgumentException e) {
            estado = Pedido.EstadoPedido.PENDIENTE;
        }

        // Crear pedido
        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .usuario(usuario)
                .estado(estado)
                .kilometros(request.getKilometros())
                .detalles(new ArrayList<>())
                .build();

        // Procesar detalles y calcular totales
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal gananciaTotal = BigDecimal.ZERO;

        for (PedidoDetalleRequest detalleRequest : request.getDetalles()) {
            // Validar producto
            Producto producto = productoRepository.findById(detalleRequest.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", detalleRequest.getIdProducto()));

            // Verificar stock del dueño
            Stock stock = stockRepository.findStockDuenoPorProducto(producto.getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("Stock no encontrado para producto: " + producto.getNombre()));

            if (stock.getCantidadActual() < detalleRequest.getCantidad()) {
                throw new StockInsuficienteException(
                        String.format("Stock insuficiente para %s. Disponible: %d, Solicitado: %d",
                                producto.getNombre(), stock.getCantidadActual(), detalleRequest.getCantidad()));
            }

            // Calcular valores del detalle
            BigDecimal precioUnitario = producto.getPrecioVenta();
            BigDecimal precioCompraUnitario = producto.getPrecioCompra();
            BigDecimal subtotalDetalle = precioUnitario.multiply(BigDecimal.valueOf(detalleRequest.getCantidad()));
            BigDecimal gananciaDetalle = precioUnitario.subtract(precioCompraUnitario)
                    .multiply(BigDecimal.valueOf(detalleRequest.getCantidad()));

            // Crear detalle
            PedidoDetalle detalle = PedidoDetalle.builder()
                    .pedido(pedido)
                    .producto(producto)
                    .cantidad(detalleRequest.getCantidad())
                    .precioUnitario(precioUnitario)
                    .precioCompraUnitario(precioCompraUnitario)
                    .subtotal(subtotalDetalle)
                    .ganancia(gananciaDetalle)
                    .build();

            pedido.getDetalles().add(detalle);

            // Acumular totales
            subtotal = subtotal.add(subtotalDetalle);
            gananciaTotal = gananciaTotal.add(gananciaDetalle);

            // Descontar stock
            stock.setCantidadActual(stock.getCantidadActual() - detalleRequest.getCantidad());
            stockRepository.save(stock);
            log.info("Stock descontado para producto: {} - Cantidad: {}", producto.getNombre(), detalleRequest.getCantidad());
        }

        // Calcular costo de delivery
        BigDecimal costoDelivery = calcularCostoDelivery(request.getKilometros());

        // Calcular total
        BigDecimal total = subtotal.add(costoDelivery);

        // Actualizar pedido con totales
        pedido.setSubtotal(subtotal);
        pedido.setCostoDelivery(costoDelivery);
        pedido.setTotal(total);
        pedido.setGananciaTotal(gananciaTotal);

        // Guardar pedido
        pedido = pedidoRepository.save(pedido);
        log.info("Pedido creado con ID: {} - Total: {}", pedido.getIdPedido(), total);

        return pedidoMapper.toResponse(pedido);
    }

    @Transactional
    public PedidoResponse updateEstadoPedido(Long id, String nuevoEstado) {
        log.info("Actualizando estado del pedido ID: {} a {}", id, nuevoEstado);
        
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", "id", id));

        Pedido.EstadoPedido estado = Pedido.EstadoPedido.valueOf(nuevoEstado.toUpperCase());
        pedido.setEstado(estado);
        
        pedido = pedidoRepository.save(pedido);
        log.info("Estado actualizado exitosamente");
        
        return pedidoMapper.toResponse(pedido);
    }

    /**
     * Calcula el costo de delivery según las reglas:
     * - Si km <= 6 → $6000
     * - Si km > 6 → CEIL(km) * 1000
     */
    private BigDecimal calcularCostoDelivery(BigDecimal kilometros) {
        if (kilometros.compareTo(BigDecimal.valueOf(6)) <= 0) {
            return BigDecimal.valueOf(6000);
        } else {
            // Redondear hacia arriba (CEIL)
            int kmRedondeado = kilometros.setScale(0, RoundingMode.CEILING).intValue();
            return BigDecimal.valueOf(kmRedondeado * 1000);
        }
    }
}
