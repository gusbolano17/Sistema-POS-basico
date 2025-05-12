package com.backend.back.service;

import com.backend.back.models.Usuario;
import com.backend.back.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() throws Exception {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long idUsuario) throws Exception {
        return usuarioRepository.findById(idUsuario);
    }

    @Transactional(rollbackOn = Exception.class)
    public Map<String,Object> crearUsuario(Usuario usuario) throws Exception {

        Map<String,Object> response = new HashMap<>();

        try{

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(usuario.getNombre());
            nuevoUsuario.setApellido(usuario.getApellido());
            nuevoUsuario.setEmail(usuario.getEmail());
            nuevoUsuario.setDireccion(usuario.getDireccion());
            nuevoUsuario.setTelefono(usuario.getTelefono());
            nuevoUsuario.setUsername(usuario.getUsername());
            nuevoUsuario.setPassword(usuario.getPassword());
            nuevoUsuario.setFechaCreacion(new Date());

            usuarioRepository.save(nuevoUsuario);

            response.put("msg", "Usuario guardado");
            response.put("code", 201);
            response.put("status", "created");
        }catch (Exception e){
            response.put("msg", e.getMessage());
            response.put("code", 500);
            response.put("status", "error");
        }

        return response;

    }

}
