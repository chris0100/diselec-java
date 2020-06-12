package com.diselec.service;

import com.diselec.entities.DireccionesEnvio;
import com.diselec.entities.DireccionesUsuario;

public interface DireccionesEnvioService {

    void asignarDatosPorDefecto(DireccionesEnvio direccionesEnvio, DireccionesUsuario direccionesUsuario);
}
