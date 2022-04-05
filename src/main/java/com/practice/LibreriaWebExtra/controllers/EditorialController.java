package com.practice.LibreriaWebExtra.controllers;

import com.practice.LibreriaWebExtra.entities.Editorial;
import com.practice.LibreriaWebExtra.services.EditorialService;
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
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialService editorialServicio;
    
    @GetMapping("/registro")
    public String formulario(){
        
        return "form-editorial.html";
    }
    
    @PostMapping("/registro")
    public String guardarEditorial(ModelMap modelo, @RequestParam String nombre){
        
        try {
            editorialServicio.guardar(nombre);
            
            modelo.put("exito", "Editorial registrada correctamente!");
            
            return "form-editorial.html";
        } catch (Exception e) {
            
            modelo.put("error", "No se pudo registrar la Editorial correcamente");
            
            return "form-editorial.html";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) throws Exception{
        
        modelo.put("editorial", editorialServicio.getById(id));
        
        return "form-editorial-modify.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam String nombre) throws Exception{
        
        try {
            editorialServicio.modificar(id, nombre);
            
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            modelo.put("error", "No se pudo modificar la Editorial");
            modelo.put("editorial", editorialServicio.getById(id));
            
            return "form-editorial-modify.html";
        }
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo) throws Exception{
        
        try {
            List<Editorial> listaEditorial = editorialServicio.listarEditoriales();
            
            modelo.addAttribute("editoriales", listaEditorial);
            
            return "list-editorial.html";
        } catch (Exception e) {
            return "index.html";
        }
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id){
        
        try {
            editorialServicio.darDeBaja(id);
            
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id){
        
        try {
            editorialServicio.darDeAlta(id);
            
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
