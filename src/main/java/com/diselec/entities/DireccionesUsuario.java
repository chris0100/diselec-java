package com.diselec.entities;

import javax.persistence.*;

@Entity
public class DireccionesUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreReceptor;
    private String direccionUno;
    private String direccionDos;
    private String ciudad;
    private String departamento;
    private String pais;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    Usuario usuario;


    ///////////////////////////
    ////////GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreReceptor() {
        return nombreReceptor;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }

    public String getDireccionUno() {
        return direccionUno;
    }

    public void setDireccionUno(String direccionUno) {
        this.direccionUno = direccionUno;
    }

    public String getDireccionDos() {
        return direccionDos;
    }

    public void setDireccionDos(String direccionDos) {
        this.direccionDos = direccionDos;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
