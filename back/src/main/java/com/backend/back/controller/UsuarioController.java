package com.backend.back.controller;

import com.backend.back.models.Usuario;
import com.backend.back.models.dtos.UsuarioDto;
import com.backend.back.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/v1/rest/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public ResponseEntity<?> listar() throws Exception{
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> obtenerUsuarioId(@PathVariable("id") Long id) throws Exception{
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    @GetMapping("/listar/nombre/{name}")
    public ResponseEntity<?> listarUsuariosNombre(@PathVariable("name") String name) throws Exception{
        return usuarioService.listarUsuariosNombre(name);
    }

    @GetMapping("/obtener/nombre/{nombre}")
    public ResponseEntity<?> buscarUsuarioNombre(@PathVariable("nombre") String nombre) throws Exception{
        return usuarioService.buscarUsuarioNombre(nombre);
    }

    @GetMapping("/listar/fechas/{fi}/{ff}")
    public ResponseEntity<?> listarUsuariosFechas(@PathVariable("fi") Date fi, @PathVariable("ff") Date ff) throws Exception{
        return usuarioService.listarUsuariosFecha(fi, ff);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody UsuarioDto usuario) throws Exception {
        return usuarioService.crearUsuario(usuario);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable("id") Long id, @RequestBody UsuarioDto usuario) throws Exception {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("id") Long id) throws Exception {
        return usuarioService.eliminarUsuario(id);
    }

}
