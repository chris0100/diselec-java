package com.diselec.repository;

import com.diselec.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Long> {

    Usuario findByUsername(String username);

    Usuario findByEmail(String email);

}
