package com.diselec.service;

import com.diselec.entities.*;
import com.diselec.repository.RoleRepository;
import com.diselec.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepositoryObj;

    @Autowired
    private RoleRepository roleRepositoryObj;


    @Override
    public Usuario crearUsuario(Usuario usuario, Set<UserRole> userRoles) throws Exception {
        //Busca si el usuario existe
        if(usuarioRepositoryObj.findByUsername(usuario.getUsername()) != null){
            System.out.println("este usuario ya existe en la bd");
            return null;
        }
        else{
            for (UserRole ur : userRoles){
                roleRepositoryObj.save(ur.getRole());
            }

            //actualizo los userRoles del usuario
            usuario.getUserRoles().addAll(userRoles);


            //se instancian listas de compras,direcciones y pagos
            CarritoDeCompras carritoDeCompras = new CarritoDeCompras();
            carritoDeCompras.setUsuario(usuario);
            usuario.setCarritoDeCompras(carritoDeCompras);

            usuario.setDireccionesUsuarioList(new ArrayList<DireccionesUsuario>());
            usuario.setMetodosPagoUsuarioList(new ArrayList<MetodosPagoUsuario>());


            return usuarioRepositoryObj.save(usuario);
        }
    }

    @Override
    public boolean save(Usuario usuario) {
        try{
            usuarioRepositoryObj.save(usuario);
            return true;
        }catch (Exception e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepositoryObj.findByEmail(email);
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepositoryObj.findByUsername(username);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepositoryObj.findById(id).get();
    }
}
