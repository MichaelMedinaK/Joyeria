package com.joyeriajoy.joyeria_back.repository;

import com.joyeriajoy.joyeria_back.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByEmail(String email);
    
    Optional<Cliente> findByDocumento(String documento);
}
