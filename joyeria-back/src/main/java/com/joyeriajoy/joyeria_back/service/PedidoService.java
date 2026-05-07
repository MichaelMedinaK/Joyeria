package com.joyeriajoy.joyeria_back.service;

import com.joyeriajoy.joyeria_back.dto.pedido.PedidoDetalleRequest;
import com.joyeriajoy.joyeria_back.dto.pedido.PedidoRequest;
import com.joyeriajoy.joyeria_back.dto.pedido.PedidoResponse;
import com.joyeriajoy.joyeria_back.dto.pedido.PedidoUpdateRequest;
import com.joyeriajoy.joyeria_back.exception.BadRequestException;
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
    private final EstadoPedidoRepository estadoPedidoRepository;
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
        EstadoPedido estadoPedido = estadoPedidoRepository.findByCodigo(estado.toUpperCase())
                .orElseThrow(() -> new BadRequestException("Estado inválido: " + estado));
        return pedidoRepository.findByEstadoPedido(estadoPedido).stream()
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

        // Validar o asignar estado
        EstadoPedido estadoPedido;
        if (request.getEstado() != null && !request.getEstado().isEmpty()) {
            estadoPedido = estadoPedidoRepository.findByCodigo(request.getEstado().toUpperCase())
                    .orElse(estadoPedidoRepository.findByCodigo("PENDIENTE")
                            .orElseThrow(() -> new BadRequestException("Estado PENDIENTE no configurado en la base de datos")));
        } else {
            estadoPedido = estadoPedidoRepository.findByCodigo("PENDIENTE")
                    .orElseThrow(() -> new BadRequestException("Estado PENDIENTE no configurado en la base de datos"));
        }

        // Crear pedido
        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .usuario(usuario)
                .estadoPedido(estadoPedido)
                .kilometros(request.getKilometros())
                .fechaEntrega(request.getFechaEntrega())
                .rangoHorario(request.getRangoHorario())
                .tipoPago(request.getTipoPago())
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

        EstadoPedido estadoPedido = estadoPedidoRepository.findByCodigo(nuevoEstado.toUpperCase())
                .orElseThrow(() -> new BadRequestException("Estado inválido: " + nuevoEstado));
        pedido.setEstadoPedido(estadoPedido);
        
        pedido = pedidoRepository.save(pedido);
        log.info("Estado actualizado exitosamente");
        
        return pedidoMapper.toResponse(pedido);
    }

    @Transactional
    public PedidoResponse updatePedido(Long id, PedidoUpdateRequest request) {
        log.info("Actualizando pedido ID: {}", id);
        
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", "id", id));

        boolean recalcularTotales = false;

        // Actualizar cliente (dirección)
        if (request.getIdCliente() != null) {
            Cliente nuevoCliente = clienteRepository.findById(request.getIdCliente())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", request.getIdCliente()));
            pedido.setCliente(nuevoCliente);
            log.info("Cliente actualizado a: {}", nuevoCliente.getNombre());
        }

        // Actualizar estado
        if (request.getEstado() != null && !request.getEstado().isEmpty()) {
            EstadoPedido estadoPedido = estadoPedidoRepository.findByCodigo(request.getEstado().toUpperCase())
                    .orElseThrow(() -> new BadRequestException("Estado inválido: " + request.getEstado()));
            pedido.setEstadoPedido(estadoPedido);
            log.info("Estado actualizado a: {}", request.getEstado());
        }

        // Actualizar kilómetros
        if (request.getKilometros() != null) {
            pedido.setKilometros(request.getKilometros());
            recalcularTotales = true;
            log.info("Kilómetros actualizados a: {}", request.getKilometros());
        }

        // Actualizar fecha de entrega
        if (request.getFechaEntrega() != null) {
            pedido.setFechaEntrega(request.getFechaEntrega());
            log.info("Fecha de entrega actualizada a: {}", request.getFechaEntrega());
        }

        // Actualizar rango horario
        if (request.getRangoHorario() != null && !request.getRangoHorario().isEmpty()) {
            pedido.setRangoHorario(request.getRangoHorario());
            log.info("Rango horario actualizado a: {}", request.getRangoHorario());
        }

        // Actualizar tipo de pago
        if (request.getTipoPago() != null && !request.getTipoPago().isEmpty()) {
            pedido.setTipoPago(request.getTipoPago());
            log.info("Tipo de pago actualizado a: {}", request.getTipoPago());
        }

        // Actualizar monto en efectivo
        if (request.getEfectivo() != null) {
            pedido.setEfectivo(request.getEfectivo());
            log.info("Monto en efectivo actualizado a: {}", request.getEfectivo());
        }

        // Actualizar monto en transferencia
        if (request.getTransferencia() != null) {
            pedido.setTransferencia(request.getTransferencia());
            log.info("Monto en transferencia actualizado a: {}", request.getTransferencia());
        }

        // Actualizar detalles (productos y cantidades)
        if (request.getDetalles() != null && !request.getDetalles().isEmpty()) {
            // Devolver stock de los detalles actuales
            for (PedidoDetalle detalleActual : pedido.getDetalles()) {
                Stock stock = stockRepository.findStockDuenoPorProducto(detalleActual.getProducto().getIdProducto())
                        .orElseThrow(() -> new ResourceNotFoundException("Stock no encontrado para producto: " + detalleActual.getProducto().getNombre()));
                stock.setCantidadActual(stock.getCantidadActual() + detalleActual.getCantidad());
                stockRepository.save(stock);
                log.info("Stock devuelto para producto: {} - Cantidad: {}", detalleActual.getProducto().getNombre(), detalleActual.getCantidad());
            }

            // Limpiar detalles actuales
            pedido.getDetalles().clear();

            // Crear nuevos detalles
            BigDecimal subtotal = BigDecimal.ZERO;
            BigDecimal gananciaTotal = BigDecimal.ZERO;

            for (PedidoDetalleRequest detalleRequest : request.getDetalles()) {
                // Validar producto
                Producto producto = productoRepository.findById(detalleRequest.getIdProducto())
                        .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", detalleRequest.getIdProducto()));

                // Verificar stock
                Stock stock = stockRepository.findStockDuenoPorProducto(producto.getIdProducto())
                        .orElseThrow(() -> new ResourceNotFoundException("Stock no encontrado para producto: " + producto.getNombre()));

                if (stock.getCantidadActual() < detalleRequest.getCantidad()) {
                    throw new StockInsuficienteException(
                            String.format("Stock insuficiente para %s. Disponible: %d, Solicitado: %d",
                                    producto.getNombre(), stock.getCantidadActual(), detalleRequest.getCantidad()));
                }

                // Calcular valores
                BigDecimal precioUnitario = producto.getPrecioVenta();
                BigDecimal precioCompraUnitario = producto.getPrecioCompra();
                BigDecimal subtotalDetalle = precioUnitario.multiply(BigDecimal.valueOf(detalleRequest.getCantidad()));
                BigDecimal gananciaDetalle = precioUnitario.subtract(precioCompraUnitario)
                        .multiply(BigDecimal.valueOf(detalleRequest.getCantidad()));

                // Crear nuevo detalle
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

                // Descontar nuevo stock
                stock.setCantidadActual(stock.getCantidadActual() - detalleRequest.getCantidad());
                stockRepository.save(stock);
                log.info("Stock descontado para producto: {} - Cantidad: {}", producto.getNombre(), detalleRequest.getCantidad());
            }

            // Actualizar totales del pedido
            pedido.setSubtotal(subtotal);
            pedido.setGananciaTotal(gananciaTotal);
            recalcularTotales = true;
            log.info("Detalles actualizados - Subtotal: {}", subtotal);
        }

        // Recalcular totales si es necesario
        if (recalcularTotales) {
            BigDecimal costoDelivery = calcularCostoDelivery(pedido.getKilometros());
            BigDecimal total = pedido.getSubtotal().add(costoDelivery);
            pedido.setCostoDelivery(costoDelivery);
            pedido.setTotal(total);
            log.info("Totales recalculados - Delivery: {}, Total: {}", costoDelivery, total);
        }

        // Guardar cambios
        pedido = pedidoRepository.save(pedido);
        log.info("Pedido actualizado exitosamente");
        
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
