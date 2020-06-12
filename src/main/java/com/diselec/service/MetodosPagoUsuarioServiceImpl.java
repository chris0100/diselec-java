package com.diselec.service;

import com.diselec.entities.MetodosPagoUsuario;
import com.diselec.entities.Usuario;
import com.diselec.repository.MetodosPagoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetodosPagoUsuarioServiceImpl implements  MetodosPagoUsuarioService{

    @Autowired
    MetodosPagoUsuarioRepository metodosPagoUsuarioRepositoryObj;

    @Override
    public List<MetodosPagoUsuario> listaMetodosPagoUsuario(Usuario usuario) {
        return metodosPagoUsuarioRepositoryObj.findByUsuario(usuario);
    }

    @Override
    public void save(MetodosPagoUsuario metodosPagoUsuario) {
        metodosPagoUsuarioRepositoryObj.save(metodosPagoUsuario);
    }

    @Override
    public MetodosPagoUsuario findById(Long id) {
        return metodosPagoUsuarioRepositoryObj.findById(id).get();
    }

    @Override
    public void delete(MetodosPagoUsuario metodosPagoUsuario) {
        metodosPagoUsuarioRepositoryObj.delete(metodosPagoUsuario);
    }
}
