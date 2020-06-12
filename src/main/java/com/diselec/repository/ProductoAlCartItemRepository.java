package com.diselec.repository;

import com.diselec.entities.CartItem;
import com.diselec.entities.ProductoAlCartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductoAlCartItemRepository extends CrudRepository<ProductoAlCartItem,Long> {

    void deleteByCartItem(CartItem cartItem);
}
