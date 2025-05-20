package com.backend.back.service;

import com.backend.back.models.*;
import com.backend.back.models.dtos.DetalleVentaReq;
import com.backend.back.models.dtos.VentaReq;
import com.backend.back.models.enums.EstadoVentas;
import com.backend.back.repository.IDetalleVentaRepository;
import com.backend.back.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class VentasService {

    @Autowired
    private InventarioService inventarioService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private IVentaRepository ventaRepository;
    @Autowired
    private IDetalleVentaRepository detalleVentaRepository;

    public List<Ventas> listarVentas() throws Exception {
        return ventaRepository.findAll();
    }

    public Optional<Ventas> buscarVentaPorId(Long id) throws Exception {
        return ventaRepository.findById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> realizarVenta(VentaReq ventaReq) throws Exception {
        try {

            if (ventaReq.producto().isEmpty()){
                return responseBuilder("No se ha ingresado ningun producto", 400, "bad request", null);
            }

            List<Map<String,Object>> detalles = new ArrayList<>();
            Double totalVenta = 0.0;

            for (DetalleVentaReq det  : ventaReq.producto()) {
                Inventario inventarioExist = inventarioService.obtenerPorNombre(det.producto())
                        .orElseThrow(() -> new Exception("El producto no existe"));

                if (inventarioExist.getStock() < det.cantidad()){
                    return responseBuilder("Stock insuficiente para: " + det.producto(), 400, "bad request", null);
                }

                Map<String,Object> detalle = new HashMap<>();
                detalle.put("producto", inventarioExist);
                detalle.put("cantidad", det.cantidad());
                detalles.add(detalle);

                totalVenta += (inventarioExist.getPrecio() * det.cantidad());

                inventarioExist.setStock(inventarioExist.getStock() - det.cantidad());
                inventarioService.actualizarInventario(inventarioExist);

            }

            Optional<Usuario> usuarioExist = usuarioService.buscarUsuarioPorId(ventaReq.usuarioId());
            if(usuarioExist.isEmpty()){
                return responseBuilder("El usuario no existe", 400, "bad request", null);
            }

            Persona personaExist = Objects.requireNonNull(personaService
                    .buscarPersonaPorDoc(ventaReq.tipoDocCliente(), ventaReq.docCliente()).getBody()).body();

            if (personaExist != null){
                return responseBuilder("La persona no existe", 400, "bad request", null);
            }

            Ventas ventas = new Ventas();
            ventas.setFactura(ventaReq.factura());
            ventas.setClienteId(personaExist);
            ventas.setUsuarioId(usuarioExist.get());
            ventas.setFechaEmision(new Date());
            ventas.setFechaModificacion(new Date());
            ventas.setFechaVencimiento(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30));
            ventas.setEstado(EstadoVentas.PENDIENTE);
            ventas.setValorTotal(totalVenta);

            Ventas ventaGuardada = ventaRepository.save(ventas);

            for(Map<String,Object> detalle : detalles){
                crearDetalleVenta(detalle, ventaGuardada);
            }

            return responseBuilder("Venta realizada con exito", 201, "created", ventaGuardada);



        }catch (Exception e){
            return responseBuilder(e.getMessage(), 500, "error", null);
        }
    }

    public Map<String,Object> actualizarVenta(Long ventaId, VentaReq ventaReq) throws Exception {
        try {

            Ventas ventaExist = buscarVentaPorId(ventaId)
                    .orElseThrow(() -> new Exception("La venta no existe"));


            List<DetalleVentas> detallesAnteriores = detalleVentaRepository.findByVentaId(ventaExist.getId());
            for (DetalleVentas det : detallesAnteriores) {
                Inventario inventario = det.getProductoId();
                inventario.setStock(inventario.getStock() + det.getCantidad());
                inventarioService.actualizarInventario(inventario);
            }

            detalleVentaRepository.deleteAll(detallesAnteriores);

            if (ventaReq.producto().isEmpty()){
                return responseBuilder("No se ha ingresado ningun producto", 400, "bad request", null);
            }

            List<Map<String,Object>> detalles = new ArrayList<>();
            Double totalVenta = 0.0;

            for (DetalleVentaReq det  : ventaReq.producto()) {
                Inventario inventarioExist = inventarioService.obtenerPorNombre(det.producto())
                        .orElseThrow(() -> new Exception("El producto no existe"));

                if (inventarioExist.getStock() < det.cantidad()){
                    return responseBuilder("Stock insuficiente para: " + det.producto(), 400, "bad request", null);
                }

                Map<String,Object> detalle = new HashMap<>();
                detalle.put("producto", inventarioExist);
                detalle.put("cantidad", det.cantidad());
                detalles.add(detalle);

                totalVenta += (inventarioExist.getPrecio() * det.cantidad());

                inventarioExist.setStock(inventarioExist.getStock() - det.cantidad());
                inventarioService.actualizarInventario(inventarioExist);

            }

            Optional<Usuario> usuarioExist = usuarioService.buscarUsuarioPorId(ventaReq.usuarioId());
            if(usuarioExist.isEmpty()){
                return responseBuilder("El usuario no existe", 400, "bad request", null);
            }

            Persona personaExist =
                    Objects.requireNonNull(personaService.buscarPersonaPorDoc(ventaReq.tipoDocCliente(), ventaReq.docCliente())
                            .getBody()).body();
            if (personaExist != null){
                return responseBuilder("La persona no existe", 400, "bad request", null);
            }

            ventaExist.setFactura(ventaReq.factura());
            ventaExist.setClienteId(personaExist);
            ventaExist.setUsuarioId(usuarioExist.get());
            ventaExist.setFechaEmision(new Date());
            ventaExist.setFechaModificacion(new Date());
            ventaExist.setFechaVencimiento(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30));
            ventaExist.setEstado(EstadoVentas.PENDIENTE);
            ventaExist.setValorTotal(totalVenta);

            Ventas ventaActualizada = ventaRepository.save(ventaExist);

            for(Map<String,Object> detalle : detalles){
                crearDetalleVenta(detalle, ventaActualizada);
            }



            return responseBuilder("El registro de la venta ha sido actualizada", 201, "created", null);
        }catch (Exception e){
            return responseBuilder(e.getMessage(), 500, "error", null);
        }
    }

    public void actualizarEstadoVenta(Long ventaId, EstadoVentas estado) throws Exception {
        try {
            Ventas ventaExist = buscarVentaPorId(ventaId)
                    .orElseThrow(() -> new Exception("La venta no existe"));

            ventaExist.setEstado(estado);
            ventaRepository.save(ventaExist);
        } catch (Exception e){
            return;
        }
    }

    private void crearDetalleVenta(Map<String, Object> detalle, Ventas venta) throws Exception {
        DetalleVentas detalleVenta = new DetalleVentas();
        detalleVenta.setVentaId(venta);
        detalleVenta.setProductoId((Inventario) detalle.get("producto"));
        detalleVenta.setCantidad((Long) detalle.get("cantidad"));
        detalleVenta.setValorTotal(((Long) detalle.get("cantidad") * ((Inventario) detalle.get("producto")).getPrecio()));
        detalleVenta.setFechaModificacion(new Date());
        detalleVenta.setFechaCreacion(new Date());

        detalleVentaRepository.save(detalleVenta);
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
