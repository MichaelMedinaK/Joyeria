package com.joyeriajoy.joyeria_back.repository;

import com.joyeriajoy.joyeria_back.model.entity.EstadoPedido;
import com.joyeriajoy.joyeria_back.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    List<Pedido> findByClienteIdCliente(Long idCliente);
    
    List<Pedido> findByEstadoPedido(EstadoPedido estadoPedido);
    
    // Pedidos por rango de fechas
    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> findByFechaPedidoBetween(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    // Ganancia total del día
    @Query("SELECT COALESCE(SUM(p.gananciaTotal), 0.0) FROM Pedido p WHERE CAST(p.fechaPedido AS DATE) = CURRENT_DATE AND p.estadoPedido.codigo != 'CANCELADO'")
    Double calcularGananciaDiaria();
    
    // Venta total del día (suma de totales de pedidos no cancelados)
    @Query("SELECT COALESCE(SUM(p.total), 0.0) FROM Pedido p WHERE CAST(p.fechaPedido AS DATE) = CURRENT_DATE AND p.estadoPedido.codigo != 'CANCELADO'")
    Double calcularVentaDelDia();
    
    // Contar pedidos pendientes del día (no ENTREGADO ni CANCELADO)
    @Query("SELECT COUNT(p) FROM Pedido p WHERE CAST(p.fechaPedido AS DATE) = CURRENT_DATE AND p.estadoPedido.codigo NOT IN ('ENTREGADO', 'CANCELADO')")
    Long contarPedidosPendientesDelDia();
}
