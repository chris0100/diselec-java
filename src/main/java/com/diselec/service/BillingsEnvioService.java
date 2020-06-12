package com.diselec.service;

import com.diselec.entities.BillingsEnvio;
import com.diselec.entities.BillingsUsuario;

public interface BillingsEnvioService {

    void asignarDatosPorDefecto(BillingsEnvio billingsEnvio, BillingsUsuario billingsUsuario);
}
