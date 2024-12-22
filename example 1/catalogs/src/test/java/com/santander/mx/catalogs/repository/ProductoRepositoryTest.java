package com.santander.mx.catalogs.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.santander.mx.catalogs.model.Producto;

@DataJpaTest
public class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void testCrearYObtenerProducto() {
        Producto producto = new Producto();
        producto.setNombre("Producto Test");
        producto.setPrecio(100.0);

        productoRepository.save(producto);

        Optional<Producto> resultado = productoRepository.findById(producto.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Producto Test", resultado.get().getNombre());
        assertEquals(100.0, resultado.get().getPrecio());
    }

    @Test
    void testEliminarProducto() {
        Producto producto = new Producto();
        producto.setNombre("Producto Test");
        producto.setPrecio(100.0);

        productoRepository.save(producto);

        productoRepository.deleteById(producto.getId());

        Optional<Producto> resultado = productoRepository.findById(producto.getId());
        assertFalse(resultado.isPresent());
    }
}
