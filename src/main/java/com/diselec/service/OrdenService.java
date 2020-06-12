package com.diselec.service;

import com.diselec.entities.*;

import java.util.Date;

public interface OrdenService {

    Orden crearOrden(Date fechaEntrega, Date fechaOrden, String metodoEnvio,
                    DireccionesEnvio direccionesEnvio,
                    BillingsEnvio billingsEnvio, Pago pago, Usuario usuario, CarritoDeCompras carritoDeCompras);
}
