package com.diselec.entities;

import javax.persistence.*;

@Entity
public class BillingsEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreBilling;
    private String direccionUnoBilling;
    private String direccionDosBilling;
    private String ciudadBilling;
    private String departamentoBilling;
    private String paisBilling;

    @OneToOne
    private Orden orden;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreBilling() {
        return nombreBilling;
    }

    public void setNombreBilling(String nombreBilling) {
        this.nombreBilling = nombreBilling;
    }

    public String getDireccionUnoBilling() {
        return direccionUnoBilling;
    }

    public void setDireccionUnoBilling(String direccionUnoBilling) {
        this.direccionUnoBilling = direccionUnoBilling;
    }

    public String getDireccionDosBilling() {
        return direccionDosBilling;
    }

    public void setDireccionDosBilling(String direccionDosBilling) {
        this.direccionDosBilling = direccionDosBilling;
    }

    public String getCiudadBilling() {
        return ciudadBilling;
    }

    public void setCiudadBilling(String ciudadBilling) {
        this.ciudadBilling = ciudadBilling;
    }

    public String getDepartamentoBilling() {
        return departamentoBilling;
    }

    public void setDepartamentoBilling(String departamentoBilling) {
        this.departamentoBilling = departamentoBilling;
    }

    public String getPaisBilling() {
        return paisBilling;
    }

    public void setPaisBilling(String paisBilling) {
        this.paisBilling = paisBilling;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }
}
