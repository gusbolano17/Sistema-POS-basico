package com.backend.back.service;

import com.backend.back.models.Persona;
import com.backend.back.models.Usuario;
import com.backend.back.models.dtos.PersonaReq;
import com.backend.back.models.dtos.UsuarioDto;
import com.backend.back.repository.IUsuarioRepository;
import com.backend.back.util.MapperObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private MapperObjects mapperObjects;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuarioExist = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe") );

        return new User(usuarioExist.getUsername(), usuarioExist.getPassword(), List.of());
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> registrarUsuario(UsuarioDto usuarioDto) {
        try {

            PersonaReq personaReq = mapperObjects.mapperPersona(usuarioDto.personaId());
            Persona pers = this.personaService.crearPersona(personaReq).getBody().body();

            Usuario nuevoUsuario = new Usuario();

            nuevoUsuario.setUsername(usuarioDto.username());

            String passEncoded = passwordEncoder().encode(usuarioDto.password());

            nuevoUsuario.setPassword(passEncoded);
            nuevoUsuario.setPersonaId(pers);
            nuevoUsuario.setFechaCreacion(new Date());

            usuarioRepository.save(nuevoUsuario);

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
