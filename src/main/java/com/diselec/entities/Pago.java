package com.diselec.entities;

import javax.persistence.*;

@Entity
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;
    private String nombreTarjeta;
    private String nombreTitular;
    private String numeroTarjeta;
    private int mesExpiracion;
    private int yearExpiracion;
    private int cvc;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "metodosPagoUsuario")
    private BillingsUsuario billingsUsuario;

    @OneToOne
    private Orden orden;


    ///////////////////////////
    ////////GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public int getMesExpiracion() {
        return mesExpiracion;
    }

    public void setMesExpiracion(int mesExpiracion) {
        this.mesExpiracion = mesExpiracion;
    }

    public int getYearExpiracion() {
        return yearExpiracion;
    }

    public void setYearExpiracion(int yearExpiracion) {
        this.yearExpiracion = yearExpiracion;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public BillingsUsuario getBillingsUsuario() {
        return billingsUsuario;
    }

    public void setBillingsUsuario(BillingsUsuario billingsUsuario) {
        this.billingsUsuario = billingsUsuario;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }
}
