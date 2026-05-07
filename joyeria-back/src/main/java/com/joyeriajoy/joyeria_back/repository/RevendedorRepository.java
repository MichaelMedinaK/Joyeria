package com.joyeriajoy.joyeria_back.repository;

import com.joyeriajoy.joyeria_back.model.entity.Revendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevendedorRepository extends JpaRepository<Revendedor, Long> {
    
    List<Revendedor> findByActivoTrue();
}
