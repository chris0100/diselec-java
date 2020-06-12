package com.diselec.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String numeroDocumento;
    private String telefono;
    private boolean habilitado = true;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<DireccionesUsuario> direccionesUsuarioList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<MetodosPagoUsuario> metodosPagoUsuarioList;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "usuario")
    private CarritoDeCompras carritoDeCompras;

    @OneToMany(mappedBy = "usuario")
    private List<Orden> ordenList;


    ///////////////////////////////////////////
    //METODOS IMPLEMENTADOS DE USER DETAILS////
    ///////////////////////////////////////////
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return habilitado;
    }
    ///////////////////////////////////////////
    ///////////////////////////////////////////


    //GETTERS AND SETTERS
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public List<DireccionesUsuario> getDireccionesUsuarioList() {
        return direccionesUsuarioList;
    }

    public void setDireccionesUsuarioList(List<DireccionesUsuario> direccionesUsuarioList) {
        this.direccionesUsuarioList = direccionesUsuarioList;
    }

    public List<MetodosPagoUsuario> getMetodosPagoUsuarioList() {
        return metodosPagoUsuarioList;
    }

    public void setMetodosPagoUsuarioList(List<MetodosPagoUsuario> metodosPagoUsuarioList) {
        this.metodosPagoUsuarioList = metodosPagoUsuarioList;
    }

    public CarritoDeCompras getCarritoDeCompras() {
        return carritoDeCompras;
    }

    public void setCarritoDeCompras(CarritoDeCompras carritoDeCompras) {
        this.carritoDeCompras = carritoDeCompras;
    }

    public List<Orden> getOrdenList() {
        return ordenList;
    }

    public void setOrdenList(List<Orden> ordenList) {
        this.ordenList = ordenList;
    }
}
