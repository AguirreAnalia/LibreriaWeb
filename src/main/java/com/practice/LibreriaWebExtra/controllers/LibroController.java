package com.practice.LibreriaWebExtra.controllers;

import com.practice.LibreriaWebExtra.entities.Autor;
import com.practice.LibreriaWebExtra.entities.Editorial;
import com.practice.LibreriaWebExtra.entities.Libro;
import com.practice.LibreriaWebExtra.services.AutorService;
import com.practice.LibreriaWebExtra.services.EditorialService;
import com.practice.LibreriaWebExtra.services.LibroService;
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
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroService libroServicio;
    
    @Autowired
    private EditorialService editorialServicio;
    
    @Autowired
    private AutorService autorServicio;
    
    @GetMapping("/registro")
    public String formulario(){
        
        return "form-libro.html";
    }
    
    @PostMapping("/registro")
    public String guardarLibro(ModelMap modelo, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String nombreAutor, @RequestParam String nombreEditorial) {
        
        try {
            
            Autor autor = autorServicio.getByNombre(nombreAutor);
            
            Editorial editorial = editorialServicio.getByNombre(nombreEditorial);
            
            libroServicio.guardar(isbn, titulo, anio, ejemplares, autor, editorial);
            
            modelo.put("exito", "Libro registrado correctamente!");
            
            return "form-libro.html";
        } catch (Exception e) {
            
            modelo.put("error", "No se pudo registrar el Libro correctamente.");
            
            return "form-libro.html";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) throws Exception{
        
        modelo.put("libro", libroServicio.getById(id));
        
        return "form-libro-modify.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String nombreAutor, @RequestParam String nombreEditorial) throws Exception{
        
        try {
            Autor autor = autorServicio.getByNombre(nombreAutor);
            
            Editorial editorial = editorialServicio.getByNombre(nombreEditorial);
            
            libroServicio.modificar(id, isbn, titulo, anio, ejemplares, autor, editorial);
            
            return "redirect:/libro/lista";
        } catch (Exception e) {
            modelo.put("error", "No se pudo modificar el Libro");
            modelo.addAttribute("libro", libroServicio.getById(id));
            
            return "form-libro-modify.html";
        }
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo) throws Exception{
        
        try {
            List<Libro> listaLibro = libroServicio.listarLibros();
            
            modelo.addAttribute("libros", listaLibro);
            
            return "list-libro.html";
        } catch (Exception e) {
            return "index.html";
        }
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id){
        
        try {
            libroServicio.darDeBaja(id);
            
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id){
        
        try {
            libroServicio.darDeAlta(id);
            
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
