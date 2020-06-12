package com.diselec.service;

import com.diselec.entities.DireccionesUsuario;
import com.diselec.entities.Usuario;

import java.util.List;

public interface DireccionesUsuarioService {

    List<DireccionesUsuario> listaDireccionesUsuario();

    boolean save(DireccionesUsuario direccionesUsuario);

    DireccionesUsuario findById(Long id);

    void deleteByid(Long id);

    List<DireccionesUsuario> findByUsuario(Usuario usuario);
}
