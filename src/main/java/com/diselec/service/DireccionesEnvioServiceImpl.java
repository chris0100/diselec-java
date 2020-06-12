package com.diselec.service;

import com.diselec.entities.DireccionesEnvio;
import com.diselec.entities.DireccionesUsuario;
import org.springframework.stereotype.Service;

@Service
public class DireccionesEnvioServiceImpl implements DireccionesEnvioService {

    @Override
    public void asignarDatosPorDefecto(DireccionesEnvio direccionesEnvio, DireccionesUsuario direccionesUsuario) {
        direccionesEnvio.setCiudad(direccionesUsuario.getCiudad());
        direccionesEnvio.setDepartamento(direccionesUsuario.getDepartamento());
        direccionesEnvio.setDireccionDos(direccionesUsuario.getDireccionDos());
        direccionesEnvio.setDireccionUno(direccionesUsuario.getDireccionUno());
        direccionesEnvio.setNombreReceptor(direccionesUsuario.getNombreReceptor());
        direccionesEnvio.setPais(direccionesUsuario.getPais());
    }
}
