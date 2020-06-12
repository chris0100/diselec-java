package com.diselec.service;

import com.diselec.entities.CarritoDeCompras;
import com.diselec.entities.CartItem;

import java.util.List;

public interface CarritoDeComprasService {

    CarritoDeCompras actualizaCarritoCompras(CarritoDeCompras carritoDeCompras, List<CartItem> cartItemList);

    void limpiarCarrito(CarritoDeCompras carritoDeCompras);
}
