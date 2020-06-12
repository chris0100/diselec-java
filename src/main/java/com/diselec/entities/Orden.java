package com.diselec.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechaOrden;
    private Date fechaEntrega;
    private String metodoEnvio;
    private String estadoOrden;
    private BigDecimal totalOrden;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orden")
    private List<CartItem> cartItemList;

    @OneToOne(cascade = CascadeType.ALL)
    private DireccionesEnvio direccionesEnvio;

    @OneToOne(cascade = CascadeType.ALL)
    private BillingsEnvio billingsEnvio;

    @OneToOne(cascade = CascadeType.ALL)
    private Pago pago;

    @ManyToOne
    private Usuario usuario;




    ///////////////////////////
    ////////GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getMetodoEnvio() {
        return metodoEnvio;
    }

    public void setMetodoEnvio(String metodoEnvio) {
        this.metodoEnvio = metodoEnvio;
    }

    public String getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(String estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public BigDecimal getTotalOrden() {
        return totalOrden;
    }

    public void setTotalOrden(BigDecimal totalOrden) {
        this.totalOrden = totalOrden;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public DireccionesEnvio getDireccionesEnvio() {
        return direccionesEnvio;
    }

    public void setDireccionesEnvio(DireccionesEnvio direccionesEnvio) {
        this.direccionesEnvio = direccionesEnvio;
    }

    public BillingsEnvio getBillingsEnvio() {
        return billingsEnvio;
    }

    public void setBillingsEnvio(BillingsEnvio billingsEnvio) {
        this.billingsEnvio = billingsEnvio;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
