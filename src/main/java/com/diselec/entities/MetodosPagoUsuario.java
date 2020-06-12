package com.diselec.entities;

import javax.persistence.*;

@Entity
public class MetodosPagoUsuario {

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


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "metodosPagoUsuario")
    private BillingsUsuario billingsUsuario;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BillingsUsuario getBillingsUsuario() {
        return billingsUsuario;
    }

    public void setBillingsUsuario(BillingsUsuario billingsUsuario) {
        this.billingsUsuario = billingsUsuario;
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }


}
