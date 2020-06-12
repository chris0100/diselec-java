package com.diselec.repository;

import com.diselec.entities.CarritoDeCompras;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoDeComprasRepository extends CrudRepository<CarritoDeCompras,Long> {



}
