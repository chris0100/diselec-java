package com.diselec.service;

import com.diselec.entities.*;
import com.diselec.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrdenServiceImpl implements OrdenService {

    @Autowired
    private OrdenRepository ordenRepositoryObj;

    @Autowired
    private CartItemService cartItemServiceObj;



    @Override
    public synchronized Orden crearOrden(Date fechaEntrega, Date fechaOrden, String metodoEnvio,
                           DireccionesEnvio direccionesEnvio,
                           BillingsEnvio billingsEnvio, Pago pago, Usuario usuario, CarritoDeCompras carritoDeCompras) {

        List<CartItem> cartItemList = cartItemServiceObj.findByCarritoDeCompras(carritoDeCompras);

        Orden orden = new Orden();
        orden.setBillingsEnvio(billingsEnvio);
        orden.setCartItemList(cartItemList);
        orden.setDireccionesEnvio(direccionesEnvio);
        orden.setEstadoOrden("Activo");
        orden.setFechaEntrega(fechaEntrega);
        orden.setFechaOrden(fechaOrden);
        orden.setTotalOrden(carritoDeCompras.getSubTotal());
        orden.setPago(pago);
        orden.setUsuario(usuario);
        orden.setMetodoEnvio(metodoEnvio);

        //se realiza ajuste de cantidaddes en los productos comprados
        for (CartItem cartItem : cartItemList){
            Producto producto = cartItem.getProducto();
            producto.setCantidad(producto.getCantidad() - cartItem.getCantidad());
            cartItem.setOrden(orden);
        }

        billingsEnvio.setOrden(orden);
        pago.setOrden(orden);
        direccionesEnvio.setOrden(orden);
        return  ordenRepositoryObj.save(orden);
    }
}
