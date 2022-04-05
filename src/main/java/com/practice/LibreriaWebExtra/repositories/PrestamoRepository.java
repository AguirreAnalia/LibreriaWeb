package com.practice.LibreriaWebExtra.repositories;

import com.practice.LibreriaWebExtra.entities.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, String>{

    @Query("SELECT p FROM Prestamo p WHERE p.alta = :alta")
    public List<Prestamo> findByAlta(@Param("alta") Boolean alta);
    
    @Query("SELECT p FROM Prestamo p WHERE p.libro.id = :id")
    public Prestamo findByLibro(@Param("id") String id);
    
    @Query("SELECT p FROM Prestamo p WHERE p.cliente.id = :id")
    public Prestamo findByCliente(@Param("id") String id);
    
}
