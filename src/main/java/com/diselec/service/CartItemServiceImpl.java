package com.diselec.service;

import com.diselec.entities.*;
import com.diselec.repository.CartItemRepository;
import com.diselec.repository.ProductoAlCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemRepository cartItemRepositoryObj;

    @Autowired
    ProductoAlCartItemRepository productoAlCartItemRepositoryObj;




    @Override
    public List<CartItem> findByCarritoDeCompras(CarritoDeCompras carritoDeCompras) {
        return cartItemRepositoryObj.findByCarritoDeCompras(carritoDeCompras);
    }




    //falta verificar la cantidad
    @Override
    public CartItem actualizarCartItem(CartItem cartItem) {
        BigDecimal bigDecimal = new BigDecimal(cartItem.getProducto().getPrecio())
                .multiply(new BigDecimal(cartItem.getCantidad()));
        bigDecimal = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
        cartItem.setSubtotal(bigDecimal);

        cartItemRepositoryObj.save(cartItem);
        return cartItem;
    }




    @Override
    public CartItem addProductoAlCartItem(Producto producto, Usuario usuario, int cantidad) {
        List<CartItem> cartItemList = findByCarritoDeCompras(usuario.getCarritoDeCompras());


        //verifica que ya haya un producto similar en el cartItemList, para actualizar precio - cantidades
        for (CartItem cartItem : cartItemList){
            if (producto.getId().equals(cartItem.getProducto().getId())){
                cartItem.setCantidad(cartItem.getCantidad() + cantidad);
                cartItem.setSubtotal(new BigDecimal(producto.getPrecio())
                .multiply(new BigDecimal(cantidad)));

                cartItemRepositoryObj.save(cartItem);
                return cartItem;
            }
        }

        //sino existe el producto asignado a un cartItem se crea un nuevo cartItem
        CartItem cartItemNuevo = new CartItem();
        cartItemNuevo.setCarritoDeCompras(usuario.getCarritoDeCompras());
        cartItemNuevo.setProducto(producto);
        cartItemNuevo.setCantidad(cantidad);
        cartItemNuevo.setSubtotal(new BigDecimal(producto.getPrecio())
        .multiply(new BigDecimal(cantidad)));
        cartItemNuevo = cartItemRepositoryObj.save(cartItemNuevo);

        //Crea un registro del producto añadido al respectivo cartItem enlazandolos
        ProductoAlCartItem productoAlCartItem = new ProductoAlCartItem();
        productoAlCartItem.setProducto(producto);
        productoAlCartItem.setCartItem(cartItemNuevo);
        productoAlCartItemRepositoryObj.save(productoAlCartItem);

        return cartItemNuevo;
    }



    @Override
    public CartItem findById(Long id) {
        return cartItemRepositoryObj.findById(id).get();
    }


    @Override
    public void quitarCartItem(CartItem cartItem) {
        productoAlCartItemRepositoryObj.deleteByCartItem(cartItem);
        cartItemRepositoryObj.delete(cartItem);
    }

    @Override
    public void save(CartItem cartItem) {
        cartItemRepositoryObj.save(cartItem);
    }
}


















