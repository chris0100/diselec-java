package com.diselec.service;

import com.diselec.entities.CarritoDeCompras;
import com.diselec.entities.CartItem;
import com.diselec.repository.CarritoDeComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CarritoDeDeComprasServiceImpl implements CarritoDeComprasService {

    @Autowired
    private CartItemService cartItemServiceObj;

    @Autowired
    private CarritoDeComprasRepository carritoDeComprasRepositoryObj;

    @Override
    public CarritoDeCompras actualizaCarritoCompras(CarritoDeCompras carritoDeCompras, List<CartItem> cartItemList) {
        BigDecimal totalCarrito = new BigDecimal(0);

        for (CartItem cartItem : cartItemList){
            if (cartItem.getProducto().getCantidad() > 0){
                cartItemServiceObj.actualizarCartItem(cartItem);
                totalCarrito = totalCarrito.add(cartItem.getSubtotal());
            }
        }
        carritoDeCompras.setSubTotal(totalCarrito);
        carritoDeComprasRepositoryObj.save(carritoDeCompras);
        return carritoDeCompras;
    }


    @Override
    public void limpiarCarrito(CarritoDeCompras carritoDeCompras) {
        List<CartItem> cartItemList = carritoDeCompras.getCartItemList();

        for (CartItem cartItem : cartItemList){
            cartItem.setCarritoDeCompras(null);
            cartItemServiceObj.save(cartItem);
        }

        carritoDeCompras.setSubTotal(new BigDecimal(0));
        carritoDeComprasRepositoryObj.save(carritoDeCompras);
    }
}














