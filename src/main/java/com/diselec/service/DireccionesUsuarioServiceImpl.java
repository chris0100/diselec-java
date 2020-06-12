package com.diselec.service;

import com.diselec.entities.DireccionesUsuario;
import com.diselec.entities.Usuario;
import com.diselec.repository.DireccionesUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionesUsuarioServiceImpl implements DireccionesUsuarioService {

    @Autowired
    DireccionesUsuarioRepository direccionesUsuarioRepositoryObj;

    @Override
    public List<DireccionesUsuario> listaDireccionesUsuario() {
        return (List<DireccionesUsuario>) direccionesUsuarioRepositoryObj.findAll();
    }

    @Override
    public boolean save(DireccionesUsuario direccionUsuario) {
        try {
            direccionesUsuarioRepositoryObj.save(direccionUsuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public DireccionesUsuario findById(Long id) {
        return direccionesUsuarioRepositoryObj.findById(id).get();
    }


    @Override
    public void deleteByid(Long id) {
        direccionesUsuarioRepositoryObj.deleteById(id);
    }


    @Override
    public List<DireccionesUsuario> findByUsuario(Usuario usuario) {
        return direccionesUsuarioRepositoryObj.findByUsuario(usuario);
    }
}
