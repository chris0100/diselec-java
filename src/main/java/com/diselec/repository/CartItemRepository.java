package com.diselec.repository;

import com.diselec.entities.CarritoDeCompras;
import com.diselec.entities.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CartItemRepository extends CrudRepository<CartItem,Long> {

    List<CartItem> findByCarritoDeCompras(CarritoDeCompras carrito);
}
