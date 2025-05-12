package com.backend.back.controller;

import com.backend.back.models.dtos.PagoReq;
import com.backend.back.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/v1/rest/api/pagos")
public class PagosController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/realizar-pago")
    public ResponseEntity<?> realizarPago(@RequestBody PagoReq pagoReq) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.realizarPago(pagoReq));
    }

}
