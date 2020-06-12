package com.diselec.controller;


import com.diselec.entities.*;
import com.diselec.service.DireccionesUsuarioService;
import com.diselec.service.MetodosPagoUsuarioService;
import com.diselec.service.ProductoService;
import com.diselec.service.UsuarioService;
import com.diselec.utility.EncriptarUtility;
import com.diselec.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {


    @Autowired
    ProductoService productoServiceObj;

    @Autowired
    UsuarioService usuarioServiceObj;

    @Autowired
    DireccionesUsuarioService direccionesUsuarioServiceObj;

    @Autowired
    MetodosPagoUsuarioService metodosPagoUsuarioServiceObj;



    @GetMapping("/listaProductos")
    public String listaProductos(Model model){
       List<Producto> listaProductos = productoServiceObj.listaProductos();
       model.addAttribute("listaProductos", listaProductos);
       return "admin/listaProductos";
    }





    @GetMapping("/nuevoProducto")
    public String nuevoProducto(Model model){
        List<String> listaCategorias = Util.listaCategorias();
        Producto producto = new Producto();
        model.addAttribute("listaCategorias", listaCategorias);
        model.addAttribute("producto",producto);
        return "admin/formProductos";
    }




    @PostMapping("/nuevoProducto")
    public String nuevoProductoPost(@ModelAttribute("producto") Producto producto, Model model,
                                    @RequestParam("imagenProducto")MultipartFile multipartFile,
                                    RedirectAttributes attributes, BindingResult resultado) throws IOException {

        List<String> listaCategorias = Util.listaCategorias();
        System.out.println(multipartFile.getOriginalFilename());
        System.out.println("producto: " + producto.getNombre() + producto.getCategoria() + ":" + producto.getEstatus() + ":" + producto.getImagen());


        if (resultado.hasErrors()){
            model.addAttribute("producto", producto);
            model.addAttribute("listaCategorias", listaCategorias);
            System.out.println("ha existido un error");
            return "admin/formProductos";
        }


        if (!multipartFile.isEmpty()){

            String imagen = Util.saveFile(multipartFile);
            producto.setImagen(imagen);
        }

        productoServiceObj.saveProducto(producto);
        System.out.println("el producto tiene id: " + producto.getId() + " y nombre: " + producto.getNombre());

        attributes.addFlashAttribute("success","Producto añadido satisfactoriamente");
        return "redirect:/listaProductos";
    }




    @GetMapping("/editarProducto")
    public String editarProducto(@PathParam("id") Long id, Model model){
        Producto producto = productoServiceObj.findById(id);
        List<String> listaCategorias = Util.listaCategorias();

        if(producto == null){
            System.out.println("retorna a pagina de error");
            return "/index";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("listaCategorias", listaCategorias);
        return "admin/formProductos";
    }


    @GetMapping("/eliminarProducto/{id}")
    public String eliminarProducto(@PathVariable("id") Long id, RedirectAttributes atributos){
        Producto producto = productoServiceObj.findById(id);
        List<String> listaCategorias = Util.listaCategorias();

        if(producto == null){
            System.out.println("retorna a pagina de error");
            return "/index";
        }
        productoServiceObj.deleteProducto(producto);
        atributos.addFlashAttribute("success","Producto eliminado satisfactoriamente");
        return "redirect:/listaProductos";
    }





    @GetMapping("/miCuenta")
    public String miCuenta(Model model, Principal principal, @PathParam("id") Long id){
        Usuario usuario = usuarioServiceObj.findByUsername(principal.getName());

        if(usuario == null){
            System.out.println("retorna a pagina de error");
            return "/index";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("activeInfo", true);
        return "miCuenta";
    }





    @PostMapping("/miCuenta")
    public String micuentaPost(@ModelAttribute("usuario")Usuario usuario, Model model, HttpServletRequest request,
                               RedirectAttributes redireccionarAtributo, @ModelAttribute("confirmarPassword") String confirmarPassword,
                               Principal principal, RedirectAttributes attributes) throws Exception{

        Usuario usuarioLogueado = usuarioServiceObj.findByUsername(principal.getName());
        System.out.println("username usuario logueado: " + usuarioLogueado.getUsername());

        System.out.println("el password es: " + usuario.getPassword() + " y la confirmacion del password es: " + confirmarPassword);

        if (!usuario.getPassword().equals(confirmarPassword)){
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "miCuenta";
        }

        else if(usuarioServiceObj.findByUsername(usuario.getUsername()) != null && usuarioServiceObj.findByEmail(usuario.getEmail()) != null &&
                (!usuario.getUsername().trim().equals("") && !usuario.getEmail().equals("")) &&  (!usuario.getPassword().trim().equals("") && !confirmarPassword.equals("")) ){

            if(!usuario.getPassword().equals(usuarioLogueado.getPassword())){
                //SE ENCRIPTA CONTRASEÑA
                String password = usuario.getPassword();
                String passwordEncriptada = EncriptarUtility.passwordEncoder().encode(password);
                usuario.setPassword(passwordEncriptada);
            }


            //SE CREA USUARIO A TRAVES DEL SERVICIO
            if(!usuarioServiceObj.save(usuario)){
//                model.addAttribute("usuario",usuarioLogueado);
//                model.addAttribute("activeInfo", true);
                attributes.addFlashAttribute("error", "Revisa que los datos esten diligenciados correctamente !");
                return "redirect:/miCuenta?id="+usuarioLogueado.getId();
            }
            model.addAttribute("usuario",usuario);
            model.addAttribute("activeInfo", true);
            model.addAttribute("success","Informacion actualizada correctamente");

            return "miCuenta";
        }


        model.addAttribute("usuario",usuarioLogueado);
        model.addAttribute("activeInfo", true);
        model.addAttribute("error", "Revisa que los datos esten diligenciados correctamente");
        return "miCuenta";
    }




    //////////////////////////////////////////////////////////////
    /////////////////MIS DIRECCIONES//////////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping("/listaDireccionesUsuario")
    public String listaDireccionesUsuario(Model model, Principal principal){
        Usuario usuarioLogueado = usuarioServiceObj.findByUsername(principal.getName());
        List<DireccionesUsuario> listaDirecciones = direccionesUsuarioServiceObj.findByUsuario(usuarioLogueado);
        model.addAttribute("activeDirecciones",true);
        if (listaDirecciones.isEmpty()){
            model.addAttribute("listaEmpty", true);
        }
        else{
            model.addAttribute("listaEmpty",false);
            model.addAttribute("listaDirecciones", listaDirecciones);
        }

        return "direccionesUsuario";
    }




    @GetMapping("/nuevaDireccion")
    public String nuevaDireccion(Model model){
        DireccionesUsuario direccionUsuario = new DireccionesUsuario();
        model.addAttribute("direccionUsuario", direccionUsuario);
        model.addAttribute("activeDirecciones", true);
        return "formDirecciones";
    }



    @PostMapping("/nuevaDireccion")
    public String nuevaDireccionPost(@ModelAttribute("direccionUsuario") DireccionesUsuario direccionUsuario,
                                     Model model, RedirectAttributes attributes, Principal principal){
        System.out.println("la direccion de usuario tiene: " + direccionUsuario.getNombreReceptor() + " : " + direccionUsuario.getDireccionUno());
        //se guarda al usuario que agrego la direccion con su ref id.
        Usuario usuarioLogueado = usuarioServiceObj.findByUsername(principal.getName());
        direccionUsuario.setUsuario(usuarioLogueado);

        if (!direccionesUsuarioServiceObj.save(direccionUsuario)){
            model.addAttribute("error", "Verifica que los datos ingresados sean correctos");
            model.addAttribute("direccionUsuario", direccionUsuario);
            model.addAttribute("activeDirecciones", true);
        }

        attributes.addFlashAttribute("success", "Direccion guardada correctamente");
        return "redirect:/listaDireccionesUsuario";
    }





    @GetMapping("/editarDireccion")
    public String editarDireccion(@PathParam("id")Long id, Model model){
        DireccionesUsuario direccionUsuario = direccionesUsuarioServiceObj.findById(id);

        if (direccionUsuario != null){
            model.addAttribute("direccionUsuario",direccionUsuario);
            model.addAttribute("activeDirecciones", true);
            return "formDirecciones";
        }
        return "direccionesUsuario";
    }



    @GetMapping("/eliminarDireccion/{id}")
    public String eliminarDireccion(@PathVariable("id") Long id, RedirectAttributes attributes){
        DireccionesUsuario direccionUsuario = direccionesUsuarioServiceObj.findById(id);
        if (direccionUsuario != null){
            direccionesUsuarioServiceObj.deleteByid(id);
            attributes.addFlashAttribute("success", "Direccion eliminada satisfactoriamente");
            return "redirect:/listaDireccionesUsuario";
        }
        attributes.addFlashAttribute("error", "Ha ocurrido un error para localizar la direccion");
        return "redirect:/listaDireccionesUsuario";
    }


    //////////////////////////////////////////////////////////////
    /////////////////MIS METODOS DE PAGO//////////////////////////////
    //////////////////////////////////////////////////////////////
    @GetMapping("/listaMetodosPagoUsuario")
    public String listaMetodoPagoUsuario(Model model, Principal principal){
        Usuario usuario = usuarioServiceObj.findByUsername(principal.getName());
        List<MetodosPagoUsuario> metodosPagoUsuarioList = metodosPagoUsuarioServiceObj.listaMetodosPagoUsuario(usuario);
        System.out.println("la lista tiene " + metodosPagoUsuarioList.size());

        if (metodosPagoUsuarioList.isEmpty()){
            model.addAttribute("listaEmpty",true);
            model.addAttribute("activeMetodosPago",true);
            return "metodosPagoUsuario";
        }

        model.addAttribute("listaMetodosPagoUsuario",metodosPagoUsuarioList);
        model.addAttribute("activeMetodosPago",true);
        model.addAttribute("listaEmpty",false);
        return "metodosPagoUsuario";
    }



    @GetMapping("/nuevoMetodoPago")
    public String nuevoMetodoPago(Model model){
        MetodosPagoUsuario metodosPagoUsuario = new MetodosPagoUsuario();
        BillingsUsuario billingsUsuario = new BillingsUsuario();
        model.addAttribute("metodoPago", metodosPagoUsuario);
        model.addAttribute("activeMetodosPago",true);
        model.addAttribute("billingUsuario", billingsUsuario);
        return "formMetodoPago";
    }


    @PostMapping("/nuevoMetodoPago")
    public String nuevoMetodoPagoPost(@ModelAttribute("metodoPago") MetodosPagoUsuario metodosPagoUsuario,
                                      @ModelAttribute("billingUsuario") BillingsUsuario billingsUsuario,
                                      Principal principal, RedirectAttributes attributes){
        System.out.println("el id metpag : " + metodosPagoUsuario.getId() + ": el id billingUsu : " + billingsUsuario.getId());
        Usuario usuarioLogueado = usuarioServiceObj.findByUsername(principal.getName());
        System.out.println(metodosPagoUsuario.getNombreTitular() + ": " + billingsUsuario.getNombreBilling());
        metodosPagoUsuario.setBillingsUsuario(billingsUsuario);
        metodosPagoUsuario.setUsuario(usuarioLogueado);
        billingsUsuario.setMetodosPagoUsuario(metodosPagoUsuario);
        metodosPagoUsuarioServiceObj.save(metodosPagoUsuario);

        attributes.addFlashAttribute("success", "Metodo de pago realizado satisfactoriamente");
        return "redirect:/listaMetodosPagoUsuario";
    }



    @GetMapping("/editarMetodoPago")
    public String editarMetodoPago(@PathParam("id") Long id, Model model){
        MetodosPagoUsuario metodosPagoUsuario = metodosPagoUsuarioServiceObj.findById(id);
        BillingsUsuario billingsUsuario = metodosPagoUsuario.getBillingsUsuario();
        if (metodosPagoUsuario != null && billingsUsuario != null){
            model.addAttribute("metodoPago", metodosPagoUsuario);
            model.addAttribute("activeMetodosPago",true);
            model.addAttribute("billingUsuario", billingsUsuario);
            return "formMetodoPago";
        }
        return "redirect:/listaMetodosPagoUsuario";
    }


    @GetMapping("/eliminarMetodoPago/{id}")
    public String eliminarMetodoPago(@PathVariable("id") Long id, Model model){
        MetodosPagoUsuario metodosPagoUsuario =  metodosPagoUsuarioServiceObj.findById(id);
        if (metodosPagoUsuario != null){
            metodosPagoUsuarioServiceObj.delete(metodosPagoUsuario);
            return "redirect:/listaMetodosPagoUsuario";
        }
        return "redirect:/listaMetodosPagoUsuario";
    }









}
