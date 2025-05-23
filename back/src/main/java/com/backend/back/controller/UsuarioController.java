package com.backend.back.controller;

import com.backend.back.models.Usuario;
import com.backend.back.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/v1/rest/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public ResponseEntity<?> listar() throws Exception{
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody Usuario usuario) throws Exception {
        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }

}
