package com.backend.back.service;

import com.backend.back.models.Persona;
import com.backend.back.models.Usuario;
import com.backend.back.models.dtos.ResponseDTO;
import com.backend.back.models.dtos.UsuarioDto;
import com.backend.back.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private PersonaService personaService;

    public ResponseEntity<ResponseDTO<List<Usuario>>> listarUsuarios() throws Exception {
        try {
            var usuarios = usuarioRepository.findAll();

            if (usuarios.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>("No hay usuarios registrados", "error", null));
            }else{
                return ResponseEntity.ok(new ResponseDTO<>("Usuarios encontrados", "ok", usuarios));
            }

        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }
    }

    public Optional<Usuario> buscarUsuarioPorId(Long idUsuario) throws Exception {
        return usuarioRepository.findById(idUsuario);
    }

    public ResponseEntity<ResponseDTO<List<Usuario>>> listarUsuariosNombre(String name) throws Exception {
        try {
            var usuarios = usuarioRepository.listarUsuariosNombre(name);

            if (usuarios.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>("No hay usuarios registrados que coinciden con su criterio", "error", null));
            }else{
                return ResponseEntity.ok(new ResponseDTO<>("Usuarios encontrados", "ok", usuarios));
            }

        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }
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

    public ResponseEntity<ResponseDTO<List<Usuario>>> listarUsuariosFecha(Date fechaInicio, Date fechaFin) throws Exception {
        try {
            var usuarios = usuarioRepository.listarUsuariosFecha(fechaInicio, fechaFin);

            if (usuarios.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>(
                                String.format("No hay usuarios registrados entre las fechas %s y %s", fechaInicio, fechaFin),
                                "error",
                                null)
                        );
            }else{
                return ResponseEntity.ok(new ResponseDTO<>("Usuarios encontrados", "ok", usuarios));
            }

        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ResponseDTO<Usuario>> crearUsuario(UsuarioDto usuario) throws Exception {

        try{

            Usuario nuevoUsuario = new Usuario();

            ResponseDTO<Persona> personaExist = personaService
                    .buscarPersonaPorDoc(usuario.personaId().get("tipoDocumento").toString(), usuario.personaId().get("documento").toString())
                    .getBody();

            if (personaExist == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>("persona no existe", "error", null));
            }

            nuevoUsuario.setUsername(usuario.username());

            String passEncoded = passwordEncoder.encode(usuario.password());

            nuevoUsuario.setPassword(passEncoded);
            nuevoUsuario.setPersonaId(personaExist.body());
            nuevoUsuario.setFechaCreacion(new Date());

            usuarioRepository.save(nuevoUsuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO<>("Usuario guardada", "ok", nuevoUsuario));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }


    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ResponseDTO<Usuario>> actualizarUsuario(Long id ,UsuarioDto usuario) throws Exception {
        try{

            Optional<Usuario> usuarioExist = buscarUsuarioPorId(id);

            if (usuarioExist.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>("Usuario no encontrado", "error", null));
            }

            Usuario usu = usuarioExist.get();

            ResponseDTO<Persona> personaExist = personaService
                    .buscarPersonaPorDoc(usuario.personaId().get("tipoDocumento").toString(), usuario.personaId().get("documento").toString())
                    .getBody();

            assert personaExist != null;
            usu.setPersonaId(personaExist.body());
            usu.setUsername(usuario.username());
            usu.setPassword(passwordEncoder.encode(usuario.password()));

            usuarioRepository.save(usu);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO<>("Usuario actualizado", "ok", usu));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<ResponseDTO<?>> eliminarUsuario(Long id) throws Exception {

        try{
            Optional<Usuario> usuarioExist = buscarUsuarioPorId(id);

            if(usuarioExist.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>("Usuario no encontrado", "error", null));
            }

            usuarioRepository.delete(usuarioExist.get());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO<>("Usuario eliminado", "ok", null));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }

    }
}
