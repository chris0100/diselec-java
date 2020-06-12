package com.diselec.service;

import com.diselec.entities.MetodosPagoUsuario;
import com.diselec.entities.Pago;

public interface PagoService {

    void asignarDatosPorDefecto(Pago pago, MetodosPagoUsuario metodosPagoUsuario);
}
