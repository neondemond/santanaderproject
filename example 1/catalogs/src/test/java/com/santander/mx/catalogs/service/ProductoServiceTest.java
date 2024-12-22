package com.santander.mx.catalogs.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.santander.mx.catalogs.model.Producto;
import com.santander.mx.catalogs.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void testCrearProducto() {
        Producto producto = new Producto();
        producto.setNombre("Producto Test");
        producto.setPrecio(100.0);
        
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto resultado = productoService.crearProducto(producto);
        assertNotNull(resultado);
        verify(productoRepository, times(1)).save(producto);
    }
    
    @Test
    void testObtenerTodosLosProductos() {
        Producto producto1 = new Producto();
        producto1.setNombre("Producto 1");
        producto1.setPrecio(100.0);
        
        Producto producto2 = new Producto();
        producto2.setNombre("Producto 2");
        producto2.setPrecio(200.0);

        when(productoRepository.findAll()).thenReturn(List.of(producto1, producto2));

        List<Producto> productos = productoService.obtenerTodosLosProductos();

        assertEquals(2, productos.size());
        assertEquals("Producto 1", productos.get(0).getNombre());
        assertEquals("Producto 2", productos.get(1).getNombre());
    }

    @Test
    void testObtenerProductoPorId() {
        Producto producto = new Producto();
        producto.setNombre("Producto Test");
        producto.setPrecio(100.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.obtenerProductoPorId(1L).orElseThrow();

        assertNotNull(resultado);
        assertEquals("Producto Test", resultado.getNombre());
        assertEquals(100.0, resultado.getPrecio());
    }

    @Test
    void testActualizarProducto() {
        Producto productoExistente = new Producto();
        productoExistente.setId(1L);
        productoExistente.setNombre("Producto Existente");
        productoExistente.setPrecio(150.0);

        Producto productoNuevo = new Producto();
        productoNuevo.setNombre("Producto Actualizado");
        productoNuevo.setPrecio(200.0);

        when(productoRepository.existsById(1L)).thenReturn(true);
        when(productoRepository.save(productoNuevo)).thenReturn(productoNuevo);

        Producto resultado = productoService.actualizarProducto(1L, productoNuevo);

        assertNotNull(resultado);
        assertEquals("Producto Actualizado", resultado.getNombre());
        assertEquals(200.0, resultado.getPrecio());
    }

    @Test
    void testEliminarProducto() {
        Long id = 1L;

        when(productoRepository.existsById(id)).thenReturn(true);

        productoService.eliminarProducto(id);

        verify(productoRepository, times(1)).deleteById(id);
    }
    
 // Test para intentar obtener un producto que no existe
    @Test
    void testObtenerProductoPorId_NoExistente() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Producto> producto = productoService.obtenerProductoPorId(99L);

        assertFalse(producto.isPresent(), "El producto no debe estar presente");
    }

    // Test para intentar actualizar un producto que no existe
    @Test
    void testActualizarProducto_NoExistente() {
        Producto producto = new Producto();
        producto.setId(99L);
        producto.setNombre("Producto No Existente");
        producto.setPrecio(100.0);

        when(productoRepository.existsById(99L)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> {
            productoService.actualizarProducto(99L, producto);
        }, "Se esperaba que se lanzara una excepción porque el producto no existe");
    }

    // Test para intentar eliminar un producto que no existe
    @Test
    void testEliminarProducto_NoExistente() {
        when(productoRepository.existsById(99L)).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> {
            productoService.eliminarProducto(99L);
        }, "Se esperaba que se lanzara una excepción porque el producto no existe");
    }
}
