package com.diselec.controller;

import com.diselec.entities.Role;
import com.diselec.entities.UserRole;
import com.diselec.entities.Usuario;
import com.diselec.service.UsuarioService;
import com.diselec.utility.EncriptarUtility;
import com.diselec.utility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private UsuarioService usuarioServiceObj;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;


    @GetMapping({"/","/index"})
    public String home(@RequestParam(value = "logout",required = false) String logout, Model model){
        if (logout != null){
            model.addAttribute("success","ha finalizado sesion con exito");
        }
        return "index";
    }


    @GetMapping("/servicios")
    public String servicios(){
        return "servicios";
    }


    @GetMapping("/contacto")
    public String contacto(){
        return "contacto";
    }



    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model
            , Principal principal, RedirectAttributes redireccionarAtributo){
        if(error != null){
            model.addAttribute("error", "Usuario y/o contraseña incorrectas");
        }

        if(principal != null){
            redireccionarAtributo.addFlashAttribute("warning", "Ud ya ha iniciado sesion previamente");
            return "redirect:/index";
        }
        return "login";
    }



    @GetMapping("/registro")
    public String registro(@ModelAttribute("usuario") Usuario usuario, Model model){
        usuario = new Usuario();
        model.addAttribute("usuario",usuario);
        return "registro";
    }




    @PostMapping("/registroNew")
    public String registroPost(@ModelAttribute("usuario")Usuario usuario, Model model, HttpServletRequest request,
                               RedirectAttributes redireccionarAtributo, @ModelAttribute("confirmarPassword") String confirmarPassword) throws Exception{

        System.out.println("el password es: " + usuario.getPassword() + " y la confirmacion del password es: " + confirmarPassword);

        if (!usuario.getPassword().equals(confirmarPassword)){
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "registro";
        }

        else if(usuarioServiceObj.findByUsername(usuario.getUsername()) == null && usuarioServiceObj.findByEmail(usuario.getEmail()) == null &&
        (!usuario.getUsername().trim().equals("") && !usuario.getEmail().equals(""))){

            //SE ENCRIPTA CONTRASEÑA
            String password = usuario.getPassword();
            String passwordEncriptada = EncriptarUtility.passwordEncoder().encode(password);
            usuario.setPassword(passwordEncriptada);

            //SE CREA ROLE
            Role role = new Role();
            role.setRoleId(1);
            role.setName("ROLE_USER");

            //SE CREA USERROLE
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(usuario,role));

            //SE CREA USUARIO A TRAVES DEL SERVICIO
            usuarioServiceObj.crearUsuario(usuario,userRoles);

            //ORGANIZACION PARA ENVIO DE DATOS POR CORREO
            String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
            System.out.println("este es el context path: " + request.getContextPath());

            SimpleMailMessage email = mailConstructor.enviarMensaje(appUrl,request.getLocale(),usuario,password);
            mailSender.send(email);

            redireccionarAtributo.addFlashAttribute("success","Registro correcto, logueate para continuar");

            return "redirect:/login";
        }

        else if (usuarioServiceObj.findByUsername(usuario.getUsername()) != null || usuarioServiceObj.findByEmail(usuario.getEmail()) != null){
            model.addAttribute("error","Este usuario ya existe en la base de datos");
            model.addAttribute("usuario",usuario);
            return "registro";
        }
        return "registro";
    }


    @GetMapping("/olvideContraseña")
    public String olvideContraseña(){
        return "olvideContraseña";
    }


    @PostMapping("/olvideContraseña")
    public String olvideContraseñaPost(@ModelAttribute("email") String email, Model model){
        Usuario usuario = usuarioServiceObj.findByEmail(email);
        if (usuario != null){
            model.addAttribute("usuario", usuario);
            return "recuperarContraseña";
        }

        model.addAttribute("error", "El correo no se encuentra en la base de datos");
        return "olvideContraseña";
    }




    @PostMapping("/recuperarContraseña")
    public String recuperarContraseñaPost(@ModelAttribute("usuario") Usuario usuario, @ModelAttribute("password") String password,
                                      @ModelAttribute("confirmarPassword") String confirmarPassword, Model model,
                                          RedirectAttributes redireccionarAtributo){

        System.out.println("el password es: " + usuario.getPassword() + " y la confirmacion del password es: " + confirmarPassword);
        System.out.println("el id a buscar para recuperar contraseña es: " + usuario.getId());


        if (!password.equals(confirmarPassword)){
            model.addAttribute("error", "Las contraseñas no coinciden");
            model.addAttribute("usuario", usuario);
            return "recuperarContraseña";
        }
        else{
            System.out.println("el id a buscar para recuperar contraseña es: " + usuario.getId());
            Usuario usuarioOriginal = usuarioServiceObj.findById(usuario.getId());

            if (usuarioOriginal != null){
                usuarioOriginal.setPassword(EncriptarUtility.passwordEncoder().encode(password));
                usuarioServiceObj.save(usuarioOriginal);
                redireccionarAtributo.addFlashAttribute("success", "La contraseña se ha cambiado correctamente");
                return "redirect:/login";
            }
        }
        return "/index";
    }










}
