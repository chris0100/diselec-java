package com.diselec.service;

import com.diselec.entities.UserRole;
import com.diselec.entities.Usuario;

import java.util.Set;

public interface UsuarioService {

    Usuario crearUsuario(Usuario usuario, Set<UserRole> userRoles) throws Exception;

    boolean save(Usuario usuario);

    Usuario findByEmail(String email);

    Usuario findByUsername(String username);

    Usuario findById(Long id);
}
