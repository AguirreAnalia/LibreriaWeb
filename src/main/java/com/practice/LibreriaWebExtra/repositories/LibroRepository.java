package com.practice.LibreriaWebExtra.repositories;

import com.practice.LibreriaWebExtra.entities.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String>{
    
    @Query("SELECT l FROM Libro l WHERE l.isbn = :isbn")
    public Libro findByIsbn(@Param("isbn") Long isbn);
    
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro findByTitutlo(@Param("titulo") String titulo);
    
    @Query("SELECT l FROM Libro l WHERE l.alta = :alta")
    public List<Libro> findByAlta(@Param("alta") Boolean alta);
    
    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
    public Libro findByAutor(@Param("nombre") String nombre);
    
    @Query("SELECT l FROM Libro l WHERE l.editorial.nombre = :nombre")
    public Libro findByEditorial(@Param("nombre") String nombre);
    
}
