package com.diselec.service;

import com.diselec.entities.MetodosPagoUsuario;
import com.diselec.entities.Usuario;

import java.util.List;

public interface MetodosPagoUsuarioService {

    List<MetodosPagoUsuario> listaMetodosPagoUsuario(Usuario usuario);

    void save(MetodosPagoUsuario metodosPagoUsuario);

    MetodosPagoUsuario findById(Long id);

    void delete(MetodosPagoUsuario metodosPagoUsuario);
}
