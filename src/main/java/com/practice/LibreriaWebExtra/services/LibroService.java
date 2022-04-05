package com.practice.LibreriaWebExtra.services;

import com.practice.LibreriaWebExtra.entities.Autor;
import com.practice.LibreriaWebExtra.entities.Editorial;
import com.practice.LibreriaWebExtra.entities.Libro;
import com.practice.LibreriaWebExtra.repositories.AutorRepository;
import com.practice.LibreriaWebExtra.repositories.LibroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    @Autowired
    LibroRepository libroRepositorio;
    
    @Autowired
    AutorRepository autorRepositorio;

    @Transactional
    public Libro guardar(Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws Exception {

        validate(isbn, titulo, anio, ejemplares);

        if (libroRepositorio.findByIsbn(isbn) != null) {
            throw new Exception("El libro ya existe.");
        }

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setEjemplaresRestantes(ejemplares);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        return libroRepositorio.save(libro);
    }
    
    @Transactional
    public Libro modificar(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws Exception {

        validate(isbn, titulo, anio, ejemplares);

        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            
            return libroRepositorio.save(libro);
        } else {
            throw new Exception("No se pudo encontrar el libro.");
        }
    }
    
    @Transactional
    public void darDeBaja(String id) throws Exception{
        
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);
            
            libroRepositorio.save(libro);
        } else {
            throw new Exception("No se pudo encontrar el libro.");
        }
    }
    
    @Transactional
    public void darDeAlta(String id) throws Exception{
        
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(true);
            
            libroRepositorio.save(libro);
        } else {
            throw new Exception("No se pudo encontrar la editorial.");
        }
    }
    
    @Transactional(readOnly = true)
    public Libro getById(String id) throws Exception{
        if (libroRepositorio.getById(id) == null){
            throw new Exception("No se pudo encontrar el libro.");
        }
        return libroRepositorio.getById(id);
    }
    
    @Transactional(readOnly = true)
    public List<Libro> listarLibros(){
        return libroRepositorio.findAll();
    }

    public void validate(Long isbn, String titulo, Integer anio, Integer ejemplares) throws Exception {

        if (isbn == null || isbn <= 0) {
            throw new Exception("Statement 'isbn' no puede ser null ni menor a 1");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new Exception("Statement 'titulo' no puede ser null ni estar vacio");
        }
        if (anio == null || anio > 2022) { //CREAR VARIABLE DATE PARA VALIDAR
            throw new Exception("Statement 'anio' no puede ser null ni ser mayor al anio actual [2022]");
        }
        if (ejemplares == null || ejemplares <= 0) {
            throw new Exception("Statement 'ejemplares' no puede ser null ni negativo");
        }
    }
}
