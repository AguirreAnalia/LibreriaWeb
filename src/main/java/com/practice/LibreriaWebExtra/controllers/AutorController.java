 package com.practice.LibreriaWebExtra.controllers;

import com.practice.LibreriaWebExtra.entities.Autor;
import com.practice.LibreriaWebExtra.services.AutorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorService autorServicio;
    
    @GetMapping("/registro")
    public String formulario(){
        
        return "form-autor.html";
    }
    
    @PostMapping("/registro")
    public String guardarAutor(ModelMap modelo, @RequestParam String nombre){
        
        try {
            autorServicio.guardar(nombre);
            
            modelo.put("exito", "Autor registrado correctamente!");
            
            return "form-autor.html";
        } catch (Exception e) {
            
            modelo.put("error", "No se pudo registrar el Autor.");
            
            return "form-autor.html";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) throws Exception{
        
        modelo.put("autor", autorServicio.getById(id));
        
        return "form-autor-modify.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) throws Exception{
        
        try {
            autorServicio.modificar(id, nombre);
            
            return "redirect:/autor/lista";
        } catch (Exception e) {
            modelo.put("error", "No se pudo modificar el Autor.");
            modelo.put("autor", autorServicio.getById(id));
            
            return "form-autor-modify.html";
        }
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) throws Exception {

        try {
            List<Autor> listaAutor = autorServicio.listarAutores();

            modelo.addAttribute("autores", listaAutor);

            return "list-autor.html";
        } catch (Exception e) {
            return "index.html";
        }
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id){
        
        try {
            autorServicio.darDeBaja(id);
            
            return "redirect:/autor/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id){
        
        try {
            autorServicio.darDeAlta(id);
            
            return "redirect:/autor/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
