package com.backend.back.repository;

import com.backend.back.models.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IInventarioRepository extends JpaRepository<Inventario, Long> {

    @Query("select i from Inventario i where i.nombre = :nombre")
    public Optional<Inventario> obtenerPorNombre(@Param("nombre") String nombre);

    @Query("select i from Inventario i where i.categoriaId.nombre = :categoria")
    public List<Inventario> listarPorCategoria(@Param("categoria") String categoria);

    @Query("select i from Inventario i where i.proveedorId.nombre like :prov%")
    public List<Inventario> listarPorProveedor(@Param("prov") String prov);


}
