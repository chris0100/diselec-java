package com.diselec.service;

import com.diselec.entities.CarritoDeCompras;
import com.diselec.entities.CartItem;
import com.diselec.entities.Producto;
import com.diselec.entities.Usuario;

import java.util.List;

public interface CartItemService {

    List<CartItem> findByCarritoDeCompras(CarritoDeCompras carritoDeCompras);

    CartItem actualizarCartItem(CartItem cartItem);

    CartItem addProductoAlCartItem(Producto producto, Usuario usuario, int cantidad);

    CartItem findById(Long id);

    void quitarCartItem(CartItem cartItem);

    void save(CartItem cartItem);
}
