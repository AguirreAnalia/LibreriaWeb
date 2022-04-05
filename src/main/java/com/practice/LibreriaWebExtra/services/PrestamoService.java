package com.practice.LibreriaWebExtra.services;

import com.practice.LibreriaWebExtra.entities.Prestamo;
import com.practice.LibreriaWebExtra.repositories.PrestamoRepository;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {

    @Autowired
    PrestamoRepository prestamoRepositorio;
    
    @Transactional
    public Prestamo guardar(String id, Date fechaPrestamo, Date fechaDevolucion) throws Exception{
        
        validate(fechaPrestamo, fechaDevolucion);
        
        if (prestamoRepositorio.findById(id) != null) {
            throw new Exception("El prestamo ya existe.");
        }
        
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);
        
        return prestamoRepositorio.save(prestamo);
    }
    
    @Transactional
    public Prestamo modificar(String id, Date fechaPrestamo, Date fechaDevolucion) throws Exception{
        
        validate(fechaPrestamo, fechaDevolucion);
        
        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Prestamo prestamo = respuesta.get();
            prestamo.setFechaPrestamo(fechaPrestamo);
            prestamo.setFechaDevolucion(fechaDevolucion);
            
            return prestamoRepositorio.save(prestamo);
        } else {
            throw new Exception("No se pudo encontrar el prestamo.");
        }
    }
    
    @Transactional
    public void darDeBaja(String id) throws Exception{
        
        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);
        
        if (respuesta.isPresent()){
            Prestamo prestamo = respuesta.get();
            prestamo.setAlta(false);
        } else {
            throw new Exception("No se pudo encontrar el prestamo.");
        }
    }
    
    @Transactional
    public void DarDeAlta(String id) throws Exception{
        
        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Prestamo prestamo = respuesta.get();
            prestamo.setAlta(true);
        } else {
            throw new Exception("No se pudo encontrar el prestamo.");
        }
    }
    
    @Transactional(readOnly = true)
    public Prestamo getById(String id) throws Exception{
        if (prestamoRepositorio.getById(id) == null) {
            throw new Exception("No se pudo encontrar el prestamo");
        }
        return prestamoRepositorio.getById(id);
    }
    
    public void validate(Date fechaPrestamo, Date fechaDevolucion) throws Exception{
        
        if (fechaPrestamo == null) {
            throw new Exception("Statement 'fechaPrestamo' no puede ser null.");
        }
        if (fechaDevolucion == null) {
            throw new Exception("Statement 'fechaDevolucion' no puede ser null.");
        }
    }
}
