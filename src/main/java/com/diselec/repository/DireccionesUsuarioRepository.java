package com.diselec.repository;

import com.diselec.entities.DireccionesUsuario;
import com.diselec.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionesUsuarioRepository extends CrudRepository<DireccionesUsuario,Long> {

    List<DireccionesUsuario> findByUsuario(Usuario usuario);


}
