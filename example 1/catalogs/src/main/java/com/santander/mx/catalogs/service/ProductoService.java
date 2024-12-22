package com.santander.mx.catalogs.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santander.mx.catalogs.model.Producto;
import com.santander.mx.catalogs.repository.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productoRepository;

	@Transactional
	public Producto crearProducto(@Valid Producto producto) {
		return productoRepository.save(producto);
	}

	public List<Producto> obtenerTodosLosProductos() {
		return productoRepository.findAll();
	}

	public Optional<Producto> obtenerProductoPorId(Long id) {
		return productoRepository.findById(id);
	}

	@Transactional
	public Producto actualizarProducto(Long id, Producto producto) {
		if (!productoRepository.existsById(id)) {
			throw new NoSuchElementException();
		}
		producto.setId(id);
		return productoRepository.save(producto);
	}

	@Transactional
	public void eliminarProducto(Long id) {
		if (!productoRepository.existsById(id)) {
			throw new NoSuchElementException();
		}
		productoRepository.deleteById(id);
	}
}
