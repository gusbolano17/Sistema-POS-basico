package com.backend.back.repository;

import com.backend.back.models.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVentaRepository extends JpaRepository<Ventas, Long>{
}
