package com.diselec.service;

import com.diselec.entities.Producto;
import com.diselec.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository productoRepositoryObj;



    @Override
    public List<Producto> listaProductos() {
        return (List<Producto>) productoRepositoryObj.findAll();
    }

    @Override
    public Producto findByNombre(String nombre) {
        return productoRepositoryObj.findByNombre(nombre);
    }

    @Override
    public void saveProducto(Producto producto) {
        productoRepositoryObj.save(producto);
    }

    @Override
    public void deleteProducto(Producto producto) {
        productoRepositoryObj.delete(producto);
    }

    @Override
    public Producto findById(Long id) {
        return productoRepositoryObj.findById(id).get();
    }

    @Override
    public List<Producto> findByCategoria(String categoria) {
        return productoRepositoryObj.findByCategoria(categoria);
    }
}
