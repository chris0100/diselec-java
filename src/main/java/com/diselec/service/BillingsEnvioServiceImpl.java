package com.diselec.service;

import com.diselec.entities.BillingsEnvio;
import com.diselec.entities.BillingsUsuario;
import org.springframework.stereotype.Service;

@Service
public class BillingsEnvioServiceImpl implements BillingsEnvioService{

    @Override
    public void asignarDatosPorDefecto(BillingsEnvio billingsEnvio, BillingsUsuario billingsUsuario) {
        billingsEnvio.setCiudadBilling(billingsUsuario.getCiudadBilling());
        billingsEnvio.setDepartamentoBilling(billingsUsuario.getDepartamentoBilling());
        billingsEnvio.setDireccionDosBilling(billingsUsuario.getDireccionDosBilling());
        billingsEnvio.setDireccionUnoBilling(billingsUsuario.getDireccionUnoBilling());
        billingsEnvio.setNombreBilling(billingsUsuario.getNombreBilling());
        billingsEnvio.setPaisBilling(billingsUsuario.getPaisBilling());
    }
}
