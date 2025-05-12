package com.backend.back.repository;

import com.backend.back.models.DetalleVentas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDetalleVentaRepository extends JpaRepository<DetalleVentas,Long> {

    @Query("select dv from DetalleVentas dv where dv.ventaId.id = :ventaId")
    List<DetalleVentas> findByVentaId(@Param("ventaId") Long ventaId) throws Exception;
}
