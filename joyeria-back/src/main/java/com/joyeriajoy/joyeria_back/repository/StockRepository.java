package com.joyeriajoy.joyeria_back.repository;

import com.joyeriajoy.joyeria_back.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    
    // Stock del dueño (revendedor es NULL)
    @Query("SELECT s FROM Stock s WHERE s.producto.idProducto = :idProducto AND s.revendedor IS NULL")
    Optional<Stock> findStockDuenoPorProducto(@Param("idProducto") Long idProducto);
    
    // Stock de un revendedor específico
    @Query("SELECT s FROM Stock s WHERE s.producto.idProducto = :idProducto AND s.revendedor.idRevendedor = :idRevendedor")
    Optional<Stock> findStockRevendedorPorProducto(@Param("idProducto") Long idProducto, @Param("idRevendedor") Long idRevendedor);
    
    // Todos los stocks del dueño
    @Query("SELECT s FROM Stock s WHERE s.revendedor IS NULL")
    List<Stock> findAllStockDueno();
    
    // Todos los stocks de un revendedor
    List<Stock> findByRevendedorIdRevendedor(Long idRevendedor);
    
    // Productos con stock bajo del dueño
    @Query("SELECT s FROM Stock s WHERE s.revendedor IS NULL AND s.cantidadActual <= s.stockMinimo")
    List<Stock> findStockBajoDueno();
}
