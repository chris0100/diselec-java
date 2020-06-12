package com.diselec.service;

import com.diselec.entities.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> listaProductos();

    Producto findByNombre(String nombre);

    void saveProducto(Producto producto);

    void deleteProducto(Producto producto);

    Producto findById(Long id);

    List<Producto> findByCategoria(String categoria);
}
