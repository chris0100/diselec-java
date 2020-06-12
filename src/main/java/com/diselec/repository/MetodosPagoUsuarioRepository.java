package com.diselec.repository;

import com.diselec.entities.MetodosPagoUsuario;
import com.diselec.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetodosPagoUsuarioRepository extends CrudRepository<MetodosPagoUsuario,Long> {

    List<MetodosPagoUsuario> findByUsuario (Usuario usuario);
}
