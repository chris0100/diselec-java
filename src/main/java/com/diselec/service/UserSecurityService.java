package com.diselec.service;

import com.diselec.entities.Usuario;
import com.diselec.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepositoryObj;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositoryObj.findByUsername(username);

        if(null == usuario){
            System.out.println("No ha logrado encontrarse el usuario");
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return usuario;
    }
}
