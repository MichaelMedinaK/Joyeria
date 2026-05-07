package com.joyeriajoy.joyeria_back.repository;

import com.joyeriajoy.joyeria_back.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Métodos de consulta personalizados si se necesitan en el futuro
}
