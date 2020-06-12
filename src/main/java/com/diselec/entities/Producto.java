package com.diselec.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String nombre;

    @Column(columnDefinition = "text")
    private String descripcion;
    private double precio;
    private String estatus = "Activo";
    private int cantidad;
    private String categoria;
    private String imagen = "baseProducto.png";

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private List<ProductoAlCartItem> productoAlCartItemList;



    public Producto() {
    }


    ///////////////////////////
    ////////GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<ProductoAlCartItem> getProductoAlCartItemList() {
        return productoAlCartItemList;
    }

    public void setProductoAlCartItemList(List<ProductoAlCartItem> productoAlCartItemList) {
        this.productoAlCartItemList = productoAlCartItemList;
    }
}
