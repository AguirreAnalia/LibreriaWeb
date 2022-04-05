package com.practice.LibreriaWebExtra.repositories;

import com.practice.LibreriaWebExtra.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>{

    @Query("SELECT c FROM Cliente c WHERE c.documento = :documento")
    public Cliente findByDocumento(@Param("documento") Long documento);
    
}
