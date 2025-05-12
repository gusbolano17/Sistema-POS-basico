package com.backend.back.controller;

import com.backend.back.models.Usuario;
import com.backend.back.models.dtos.UserReq;
import com.backend.back.service.AuthService;
import com.backend.back.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/v1/rest/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserReq userReq) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userReq.username(), userReq.password()));

            String token = jwtUtil.generateToken(userReq.username());

            return new ResponseEntity<>(Map.of("msg","usuario autenticado","body", token), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
