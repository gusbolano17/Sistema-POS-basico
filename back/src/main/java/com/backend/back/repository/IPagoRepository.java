package com.backend.back.repository;

import com.backend.back.models.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPagoRepository extends JpaRepository<Pagos, Long> {
}
