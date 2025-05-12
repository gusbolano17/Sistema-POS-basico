package com.backend.back.service;

import com.backend.back.models.Pagos;
import com.backend.back.models.Ventas;
import com.backend.back.models.dtos.PagoReq;
import com.backend.back.models.dtos.VentaReq;
import com.backend.back.models.enums.EstadoVentas;
import com.backend.back.repository.IDetalleVentaRepository;
import com.backend.back.repository.IPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PagoService {

    @Autowired
    private IPagoRepository pagoRepository;
    @Autowired
    private VentasService ventaService;
    @Autowired
    private IDetalleVentaRepository detalleVentaRepository;

    public Map<String,Object> realizarPago(PagoReq pagoReq) throws Exception {
        try{

            Ventas ventaExist = ventaService.buscarVentaPorId(pagoReq.ventaId())
                    .orElseThrow(() -> new Exception("La venta no existe"));

            if (ventaExist.getEstado().equals(EstadoVentas.PENDIENTE)){

                if(pagoReq.monto() < ventaExist.getValorTotal()){
                    return responseBuilder("El monto no es suficiente", 400, "bad request", null);
                }

                Pagos pago = new Pagos();
                pago.setVentaId(ventaExist);
                pago.setMonto(pagoReq.monto());
                pago.setMetodoPago(pagoReq.metodoPago());
                pago.setFechaPago(new Date());

                pagoRepository.save(pago);

                ventaService.actualizarEstadoVenta(ventaExist.getId(), EstadoVentas.PAGADO);

                return responseBuilder("Pago realizado con exito", 201, "created", pago);
            }else if (ventaExist.getEstado().equals(EstadoVentas.PAGADO)){
                return responseBuilder("La venta ya fue pagada", 400, "bad request", null);
            }else{
                return responseBuilder("La venta fue anulada", 400, "bad request", null);
            }

        } catch (Exception e) {
            return responseBuilder(e.getMessage(), 500, "error", null);
        }
    }

    private Map<String, Object> responseBuilder(String msg, int code, String status, Object data ) {
        Map<String, Object> response = new HashMap<>();
        response.put("msg", msg);
        response.put("code", code);
        response.put("status", status);
        if(code == 200 || code == 201){
            response.put("data", data);
        }
        return response;
    }

}
