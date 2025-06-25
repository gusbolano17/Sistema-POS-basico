package com.backend.back.controller;

import com.backend.back.models.Categoria;
import com.backend.back.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/v1/rest/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listar")
    public ResponseEntity<?> listarCategorias() throws Exception{
        return categoriaService.listarCategorias();
    }

    @GetMapping("/listar/nombre/{nombre}")
    public ResponseEntity<?> listarCategoriasNombre(@PathVariable String nombre) throws Exception{
        return categoriaService.listarCategoriasNombre(nombre);
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarCategoria(@RequestBody Map<String,Object> catBody) throws Exception{
        return categoriaService.agregarCategoria(catBody);
    }

}
