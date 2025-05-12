package com.backend.back.controller;

import com.backend.back.models.dtos.VentaReq;
import com.backend.back.service.VentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/v1/rest/api/ventas")
public class VentasController {

    @Autowired
    private VentasService ventasService;

    @PostMapping("/realizar-venta")
    public ResponseEntity<?> realizarVenta(@RequestBody VentaReq ventaReq) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventasService.realizarVenta(ventaReq));
    }

}
