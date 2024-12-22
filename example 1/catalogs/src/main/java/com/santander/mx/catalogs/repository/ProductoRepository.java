package com.santander.mx.catalogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santander.mx.catalogs.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Métodos adicionales de consulta si es necesario
}
