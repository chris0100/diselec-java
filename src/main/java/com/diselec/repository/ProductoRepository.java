package com.diselec.repository;

import com.diselec.entities.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductoRepository extends CrudRepository<Producto,Long> {

    Producto findByNombre(String nombre);

    List<Producto> findByCategoria(String categoria);


}
