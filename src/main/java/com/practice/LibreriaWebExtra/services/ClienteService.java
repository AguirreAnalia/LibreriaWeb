package com.practice.LibreriaWebExtra.services;

import com.practice.LibreriaWebExtra.entities.Cliente;
import com.practice.LibreriaWebExtra.repositories.ClienteRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepositorio;
    
    @Transactional
    public Cliente crear(Long documento, String nombre, String apellido, String telefono) throws Exception{
        
        validate(documento, nombre, apellido, telefono);
        
        if (clienteRepositorio.findByDocumento(documento) != null) {
            throw new Exception("No se ha encontrado un cliente con ese documento.");
        }
        
        Cliente cliente = new Cliente();
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        
        return clienteRepositorio.save(cliente);
    }
    
    @Transactional
    public Cliente modificar(String id, Long documento, String nombre, String apellido, String telefono) throws Exception{
        
        validate(documento, nombre, apellido, telefono);
        
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            
            Cliente cliente = respuesta.get();
            cliente.setDocumento(documento);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);
            
            return clienteRepositorio.save(cliente);
        } else {
            throw new Exception("No se pudo encontrar un cliente con ese ID.");
        }
        
    }
    
    @Transactional
    public void darDeBaja(String id) throws Exception{
        
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setAlta(false);
            
            clienteRepositorio.save(cliente);
        } else {
            throw new Exception("No se pudo encontrar un cliente con ese ID.");
        }
    }
    
    //Method prestamo() HERE
    @Transactional(readOnly = true)
    public Cliente getById(String id) throws Exception{
        if (clienteRepositorio.getById(id) == null){ // equals al objeto id) {
            throw new Exception("No se pudo encontrar un cliente con ese ID.");
        }
        return clienteRepositorio.getById(id);
    }
    
    
    public void validate(Long documento, String nombre, String apellido, String telefono) throws Exception{
        
        if (documento == null || documento <= 0) {
            throw new Exception("Statement 'documento' no puede ser null y debe ser mayor a 0");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("Statement 'nombre' no puede ser null ni estar vacio");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new Exception("Statement 'apellido' no puede ser null ni estar vacio");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new Exception("Statement 'telefono' no puede ser null ni estar vacio");
        }
    }
}
