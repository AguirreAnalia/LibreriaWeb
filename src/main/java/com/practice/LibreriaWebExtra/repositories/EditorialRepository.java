package com.practice.LibreriaWebExtra.repositories;

import com.practice.LibreriaWebExtra.entities.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, String>{

    @Query("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public Editorial findByNombre(@Param("nombre") String nombre);
    
    @Query("SELECT e FROM Editorial e WHERE e.alta = :alta")
    public List<Editorial> findByAlta(@Param("alta") Boolean alta);
    
}
