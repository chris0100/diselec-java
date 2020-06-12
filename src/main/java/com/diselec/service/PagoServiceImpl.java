package com.diselec.service;

import com.diselec.entities.MetodosPagoUsuario;
import com.diselec.entities.Pago;
import org.springframework.stereotype.Service;

@Service
public class PagoServiceImpl implements  PagoService{

    @Override
    public void asignarDatosPorDefecto(Pago pago, MetodosPagoUsuario metodosPagoUsuario) {
        pago.setBillingsUsuario(metodosPagoUsuario.getBillingsUsuario());
        pago.setCvc(metodosPagoUsuario.getCvc());
        pago.setMesExpiracion(metodosPagoUsuario.getMesExpiracion());
        pago.setNombreTarjeta(metodosPagoUsuario.getNombreTarjeta());
        pago.setNombreTitular(metodosPagoUsuario.getNombreTitular());
        pago.setNumeroTarjeta(metodosPagoUsuario.getNumeroTarjeta());
        pago.setTipo(metodosPagoUsuario.getTipo());
        pago.setYearExpiracion(metodosPagoUsuario.getYearExpiracion());
    }
}
