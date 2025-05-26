package com.backend.back.service;

import com.backend.back.models.Usuario;
import com.backend.back.models.dtos.ResponseDTO;
import com.backend.back.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() throws Exception {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long idUsuario) throws Exception {
        return usuarioRepository.findById(idUsuario);
    }

    public ResponseEntity<ResponseDTO<Usuario>> buscarUsuarioNombre(String username) throws Exception {
        try{
            Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
            return usuario
                    .map(value ->
                            ResponseEntity.ok(new ResponseDTO<>("Usuario encontrado", "ok", value)))
                    .orElseGet(() -> ResponseEntity.status(404)
                            .body(new ResponseDTO<>("Usuario no encontrado", "error", null)));
        }catch (DataAccessException e){
            return ResponseEntity.status(500)
                    .body(new ResponseDTO<>("Error al buscar usuario", "error", null));
        }
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

            String passEncoded = passwordEncoder.encode(usuario.getPassword());

            nuevoUsuario.setPassword(passEncoded);
            nuevoUsuario.setFechaNacimiento(usuario.getFechaNacimiento());
            nuevoUsuario.setFechaNacimiento(usuario.getFechaNacimiento());
            nuevoUsuario.setPais(usuario.getPais());
            nuevoUsuario.setDepartamentoId(usuario.getDepartamentoId());
            nuevoUsuario.setCiudadId(usuario.getCiudadId());
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
