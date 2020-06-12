package com.diselec.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;
    private BigDecimal subtotal;

    @OneToOne
    private Producto producto;

    @OneToMany(mappedBy = "cartItem")
    @JsonIgnore
    private List<ProductoAlCartItem> productoAlCartItemList;

    @ManyToOne
    @JoinColumn(name ="carrito_de_compras_id")
    private CarritoDeCompras carritoDeCompras;
//
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orden orden;



    /////////////////////////////////////
    //GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<ProductoAlCartItem> getProductoAlCartItemList() {
        return productoAlCartItemList;
    }

    public void setProductoAlCartItemList(List<ProductoAlCartItem> productoAlCartItemList) {
        this.productoAlCartItemList = productoAlCartItemList;
    }

    public CarritoDeCompras getCarritoDeCompras() {
        return carritoDeCompras;
    }

    public void setCarritoDeCompras(CarritoDeCompras carritoDeCompras) {
        this.carritoDeCompras = carritoDeCompras;
    }
    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }
}
