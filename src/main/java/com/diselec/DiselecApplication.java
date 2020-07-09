package com.diselec;

import com.diselec.entities.Role;
import com.diselec.entities.UserRole;
import com.diselec.entities.Usuario;
import com.diselec.service.ProductoService;
import com.diselec.service.UsuarioService;
import com.diselec.utility.EncriptarUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DiselecApplication implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioServiceObj;

    @Autowired
    private ProductoService productoServiceObj;

    public static void main(String[] args) {
        SpringApplication.run(DiselecApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //agregar usuario
        /*Usuario usuario = new Usuario();
        usuario.setApellido("rodriguez");
        usuario.setNombre("christia");
        usuario.setEmail("alexroguez59@gmail.com");
        usuario.setNumeroDocumento("1107066494");
        usuario.setPassword(EncriptarUtility.passwordEncoder().encode("case"));
        usuario.setTelefono("3114045322");
        usuario.setUsername("car");
        Set<UserRole> userRoles = new HashSet<>();
        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        userRoles.add(new UserRole(usuario,role));
        System.out.println("paso por aqui para crear usuario");
        //guardar usuario de prueba
        usuarioServiceObj.crearUsuario(usuario,userRoles);*/


        //agregar 1 producto de prueba
/*        Producto producto = new Producto();
        producto.setNombre("Condensador");
        producto.setDescripcion("12 patas, 10 volt");
        producto.setPrecio(25000);
        producto.setCantidad(4);
        producto.setCategoria("Potenciadores");
        productoServiceObj.saveProducto(producto);*/



    }
}

















