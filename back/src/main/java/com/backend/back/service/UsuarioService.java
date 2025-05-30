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
    public ResponseEntity<ResponseDTO<Usuario>> crearUsuario(UsuarioDto usuario) throws Exception {

        try{

            Usuario nuevoUsuario = new Usuario();

            ResponseDTO<Persona> personaExist = personaService
                    .buscarPersonaPorDoc(usuario.persona().get("tipoDocumento"), usuario.persona().get("documento"))
                    .getBody();

            if (personaExist == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO<>("persona no existe", "error", null));
            }

            nuevoUsuario.setNombre(personaExist.body().getNombre());
            nuevoUsuario.setApellido(personaExist.body().getApellido());
            nuevoUsuario.setEmail(personaExist.body().getEmail());
            nuevoUsuario.setDireccion(personaExist.body().getDireccion());
            nuevoUsuario.setTelefono(personaExist.body().getTelefono());
            nuevoUsuario.setUsername(usuario.username());

            String passEncoded = passwordEncoder.encode(usuario.password());

            nuevoUsuario.setPassword(passEncoded);
            nuevoUsuario.setTipoDocumento(personaExist.body().getTipoDocumento());
            nuevoUsuario.setDocumento(personaExist.body().getDocumento());
            nuevoUsuario.setFechaNacimiento(personaExist.body().getFechaNacimiento());
            nuevoUsuario.setFechaModificacion(new Date());
            nuevoUsuario.setPais(personaExist.body().getPais());
            nuevoUsuario.setDepartamentoId(personaExist.body().getDepartamentoId());
            nuevoUsuario.setCiudadId(personaExist.body().getCiudadId());
            nuevoUsuario.setFechaCreacion(new Date());

            usuarioRepository.save(nuevoUsuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO<>("Usuario guardada", "ok", nuevoUsuario));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(), "error", null));
        }


    }

}
