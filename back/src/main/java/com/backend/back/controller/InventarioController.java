package com.backend.back.controller;

import com.backend.back.models.dtos.InventarioReq;
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

    @GetMapping("/listar/categoria/{cat}")
    public ResponseEntity<?> buscarInventarioPorCategoria(@PathVariable("cat") String cat) throws Exception{
        return inventarioService.listarInventarioCategoria(cat);
    }

    @GetMapping("/listar/proveedor/{proveedor}")
    public ResponseEntity<?> listarInventarioPorProveedor(@PathVariable("proveedor") String proveedor) throws Exception{
        return inventarioService.listarInventarioProveedor(proveedor);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearInventario(@RequestBody InventarioReq inventario) throws Exception {
        return inventarioService.crearInventario(inventario);
    }

}
