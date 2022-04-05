package com.practice.LibreriaWebExtra.services;

import com.practice.LibreriaWebExtra.entities.Autor;
import com.practice.LibreriaWebExtra.repositories.AutorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {
    
    @Autowired
    AutorRepository autorRepositorio;
    
    @Transactional
    public Autor guardar(String nombre) throws Exception{
        
        validate(nombre);
        
        if (autorRepositorio.findByNombre(nombre) != null) {
            throw new Exception("El autor ya existe.");
        }
        
        Autor autor = new Autor();
        autor.setNombre(nombre);
        
        return autorRepositorio.save(autor);
    }
    
    @Transactional
    public Autor modificar(String id, String nombre) throws Exception{
        
        validate(nombre);
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            
            return autorRepositorio.save(autor);
        } else {
            throw new Exception("No se pudo encontrar el autor.");
        }
    }
    
    @Transactional
    public void darDeBaja(String id) throws Exception{
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);
            
            autorRepositorio.save(autor);
        } else {
            throw new Exception("No se pudo encontrar el autor.");
        }
    }
    
    @Transactional
    public void darDeAlta(String id) throws Exception{
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(true);
            
            autorRepositorio.save(autor);
        } else {
            throw new Exception("No se pudo encontrar el autor.");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Autor> listarAutores() throws Exception{
        return autorRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public Autor getById(String id) throws Exception{
        if (autorRepositorio.getById(id) == null) {
            throw new Exception("ID null.");
        }
        return autorRepositorio.getById(id);
    }
    
    @Transactional(readOnly = true)
    public Autor getByNombre(String nombre) throws Exception{
        if (autorRepositorio.findByNombre(nombre) == null){
            throw new Exception("No existe ese Autor.");
        }
        
        return autorRepositorio.findByNombre(nombre);
    }

    public void validate(String nombre) throws Exception{
        
        if (nombre == null || nombre.isEmpty()){
            throw new Exception("Statement 'nombre' no puede ser null ni estar vacio.");
        }
    }
}
