package com.practice.LibreriaWebExtra.services;

import com.practice.LibreriaWebExtra.entities.Editorial;
import com.practice.LibreriaWebExtra.repositories.EditorialRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialService {
    
    @Autowired
    EditorialRepository editorialRepositorio;
    
    @Transactional
    public Editorial guardar(String nombre) throws Exception{
        
        validate(nombre);
       
        if (editorialRepositorio.findByNombre(nombre) != null) {
            throw new Exception("La editorial ya existe.");
        } 
       
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        
        return editorialRepositorio.save(editorial);
    }
    
    @Transactional
    public Editorial modificar(String id, String nombre) throws Exception{
        
        validate(nombre);
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            
            return editorialRepositorio.save(editorial);
        } else {
            throw new Exception("No se pudo encontrar la editorial.");
        }
    }
  
    @Transactional
    public void darDeBaja(String id) throws Exception{
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            
            editorialRepositorio.save(editorial);
        } else {
            throw new Exception("No se pudo encontrar la editorial.");
        }
    }
    
    @Transactional
    public void darDeAlta(String id) throws Exception{
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(true);
            
            editorialRepositorio.save(editorial);
        } else {
            throw new Exception("No se pudo encontrar la editorial.");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales(){
        return editorialRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Editorial getById(String id) throws Exception{
        if (editorialRepositorio.getById(id) == null) {
            throw new Exception("No se pudo encontrar la editorial con ese ID.");
        }
        return editorialRepositorio.getById(id);
    }
    
    @Transactional(readOnly=true)
    public Editorial getByNombre(String nombre) throws Exception{
        
        if (editorialRepositorio.findByNombre(nombre) == null) {
            throw new Exception("No existe esa Editorial.");
        }
        
        return editorialRepositorio.findByNombre(nombre);
    }
    
    public void validate(String nombre) throws Exception{
        
        if (nombre == null || nombre.isEmpty()){
            throw new Exception("Statement 'nombre' no puede ser null ni estar vacio.");
        }
    }
}
