package com.joyeriajoy.joyeria_back.repository;

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
    
    List<Pedido> findByEstado(Pedido.EstadoPedido estado);
    
    // Pedidos por rango de fechas
    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin")
    List<Pedido> findByFechaPedidoBetween(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    // Ganancia total del día
    @Query("SELECT COALESCE(SUM(p.gananciaTotal), 0.0) FROM Pedido p WHERE CAST(p.fechaPedido AS DATE) = CURRENT_DATE AND p.estado != 'CANCELADO'")
    Double calcularGananciaDiaria();
}
