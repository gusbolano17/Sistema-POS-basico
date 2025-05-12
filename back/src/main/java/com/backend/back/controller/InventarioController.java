package com.backend.back.controller;

import com.backend.back.models.Inventario;
import com.backend.back.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/v1/rest/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/listar")
    public ResponseEntity<?> listarInventarios() throws Exception{
        return ResponseEntity.ok(inventarioService.listarInventarios());
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> buscarInventarioPorId(@PathVariable("id") Long id) throws Exception{
        return ResponseEntity.ok(inventarioService.buscarInventarioPorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearInventario(@RequestBody Inventario inventario) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.crearInventario(inventario));
    }

}
