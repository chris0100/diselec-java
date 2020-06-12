package com.diselec.controller;

import com.diselec.entities.*;
import com.diselec.service.*;
import com.diselec.utility.MailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
public class CheckOutController {

    @Autowired
    private UsuarioService usuarioServiceObj;

    @Autowired
    private DireccionesUsuarioService direccionesUsuarioServiceObj;

    @Autowired
    private MetodosPagoUsuarioService metodosPagoUsuarioServiceObj;

    @Autowired
    private DireccionesEnvioService direccionesEnvioServiceObj;

    @Autowired
    private BillingsEnvioService billingsEnvioServiceObj;

    @Autowired
    private PagoService pagoServiceObj;

    @Autowired
    private OrdenService ordenServiceObj;

    @Autowired
    private CarritoDeComprasService carritoDeComprasServiceObj;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;


    //LISTAS PARA ENVIOS,PAGOS Y BILLINGS DEFINITIVAS
    Pago pagoObj = new Pago();
    BillingsEnvio billingsEnvioObj = new BillingsEnvio();
    DireccionesEnvio direccionesEnvioObj = new DireccionesEnvio();


    @GetMapping("/checkOut")
    public String checkOut(@PathParam("id") Long id, Model model, Principal principal) {
        Usuario usuario = usuarioServiceObj.findByUsername(principal.getName());
        System.out.println("id del carrito: " + id);

        //verifica que el carrito de compras de la pag anterior coincida con la del usuario logueado
        if (!id.equals(usuario.getCarritoDeCompras().getId())) {
            return "redirect:/index";
        }

        //se obtiene la cartItemList
        List<CartItem> cartItemList = usuario.getCarritoDeCompras().getCartItemList();

        //revisa si la cartItemList esta vacia
        if (cartItemList.size() == 0) {
            model.addAttribute("listaEmpty", true);
            return "redirect:/carritoCompras";
        }

        //verifica las cantidades disponibles en los productos que se van a comprar.
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getProducto().getCantidad() < cartItem.getCantidad()) {
                model.addAttribute("stockInsuficiente", true);
                return "redirect:/carritoCompras";
            }
        }

        //listas de direcciones y metodos de pago guardadas en la cuenta del usuario
        List<DireccionesUsuario> direccionesUsuarioList = direccionesUsuarioServiceObj.findByUsuario(usuario);
        List<MetodosPagoUsuario> metodosPagoUsuarioList = metodosPagoUsuarioServiceObj.listaMetodosPagoUsuario(usuario);

        //si las listas estan vacias, enviara los campos en blanco
        if (direccionesUsuarioList.size() != 0) {
            model.addAttribute("listaDireccionesUsuario", direccionesUsuarioList);
            direccionesEnvioServiceObj.asignarDatosPorDefecto(direccionesEnvioObj, direccionesUsuarioList.get(0));
            System.out.println("debe haber cambiado el obj direccionesEnvioObj " + direccionesEnvioObj.getNombreReceptor());
            model.addAttribute("direccionesEnvio", direccionesEnvioObj);
        }

        //si las listas estan vacias, enviara los campos en blanco
        if (metodosPagoUsuarioList.size() != 0) {
            model.addAttribute("listaMetodosPagoUsuario", metodosPagoUsuarioList);
            billingsEnvioServiceObj.asignarDatosPorDefecto(billingsEnvioObj, metodosPagoUsuarioList.get(0).getBillingsUsuario());
            pagoServiceObj.asignarDatosPorDefecto(pagoObj, metodosPagoUsuarioList.get(0));
            System.out.println("debe haber cambiado el obj billingsEnvioObj " + billingsEnvioObj.getNombreBilling());
            System.out.println("debe haber cambiado el obj pagoObj " + pagoObj.getNombreTitular());
            model.addAttribute("metodoPago", pagoObj);
            model.addAttribute("billingsEnvio", billingsEnvioObj);
        }


        //mas datos para ser mostrados en la pagina
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("carritoCompras", usuario.getCarritoDeCompras());

        //pestaña para vista inicial activa
        model.addAttribute("activeDirecciones", true);

        return "checkOut";
    }


    @GetMapping("/usarDireccion")
    public String usarDireccion(@RequestParam("id") Long id, Principal principal, Model model) {
        Usuario usuario = usuarioServiceObj.findByUsername(principal.getName());

        System.out.println("el id de la direccion para usar es: " + id);
        DireccionesUsuario direccionUsar = direccionesUsuarioServiceObj.findById(id);

        //se obtiene la cartItemList
        List<CartItem> cartItemList = usuario.getCarritoDeCompras().getCartItemList();

        //listas de direcciones y metodos de pago guardadas en la cuenta del usuario
        List<DireccionesUsuario> direccionesUsuarioList = direccionesUsuarioServiceObj.findByUsuario(usuario);
        List<MetodosPagoUsuario> metodosPagoUsuarioList = metodosPagoUsuarioServiceObj.listaMetodosPagoUsuario(usuario);


        //si las listas estan vacias, enviara los campos en blanco
        if (direccionesUsuarioList.size() != 0) {
            model.addAttribute("listaDireccionesUsuario", direccionesUsuarioList);
            direccionesEnvioServiceObj.asignarDatosPorDefecto(direccionesEnvioObj, direccionUsar);
            System.out.println("debe haber cambiado el obj direccionesEnvioObj " + direccionesEnvioObj.getNombreReceptor());
            model.addAttribute("direccionesEnvio", direccionesEnvioObj);
        }

        //si las listas estan vacias, enviara los campos en blanco
        if (metodosPagoUsuarioList.size() != 0) {
            model.addAttribute("listaMetodosPagoUsuario", metodosPagoUsuarioList);
            model.addAttribute("metodoPago", pagoObj);
            model.addAttribute("billingsEnvio", billingsEnvioObj);
        }


        //mas datos para ser mostrados en la pagina
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("carritoCompras", usuario.getCarritoDeCompras());

        //pestaña para vista inicial activa
        model.addAttribute("activeDirecciones", true);

        return "checkOut";
    }


    @GetMapping("/usarTarjeta")
    public String usarTarjeta(@RequestParam("id") Long id, Model model, Principal principal) {
        MetodosPagoUsuario metodosPagoUsuario = metodosPagoUsuarioServiceObj.findById(id);
        Usuario usuario = usuarioServiceObj.findByUsername(principal.getName());

        //se obtiene la cartItemList
        List<CartItem> cartItemList = usuario.getCarritoDeCompras().getCartItemList();

        //listas de direcciones y metodos de pago guardadas en la cuenta del usuario
        List<DireccionesUsuario> direccionesUsuarioList = direccionesUsuarioServiceObj.findByUsuario(usuario);
        List<MetodosPagoUsuario> metodosPagoUsuarioList = metodosPagoUsuarioServiceObj.listaMetodosPagoUsuario(usuario);

        //verifica que el metodo de pago del usuario le pertenezca a quien esta logueado
        if (!metodosPagoUsuario.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/index";
        }

        //si las listas estan vacias, enviara los campos en blanco
        if (direccionesUsuarioList.size() != 0) {
            model.addAttribute("listaDireccionesUsuario", direccionesUsuarioList);
            model.addAttribute("direccionesEnvio", direccionesEnvioObj);
        }

        //si las listas estan vacias, enviara los campos en blanco
        if (metodosPagoUsuarioList.size() != 0) {
            pagoServiceObj.asignarDatosPorDefecto(pagoObj, metodosPagoUsuario);
            billingsEnvioServiceObj.asignarDatosPorDefecto(billingsEnvioObj, metodosPagoUsuario.getBillingsUsuario());
            model.addAttribute("listaMetodosPagoUsuario", metodosPagoUsuarioList);
            model.addAttribute("metodoPago", pagoObj);
            model.addAttribute("billingsEnvio", billingsEnvioObj);
        }


        //mas datos para ser mostrados en la pagina
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("carritoCompras", usuario.getCarritoDeCompras());

        //pestaña para vista inicial activa
        model.addAttribute("activeDirecciones", true);

        return "checkOut";
    }


    @PostMapping("/checkOut")
    public String checkOutPost(@ModelAttribute("direccionesEnvio") DireccionesEnvio direccionesEnvio,
                               @ModelAttribute("metodoPago") Pago pago,
                               @ModelAttribute("billingsEnvio") BillingsEnvio billingsEnvio,
                               @ModelAttribute("igualDireccionEnvio") String igualDireccionEnvio,
                               @ModelAttribute("tipoEnvio") String tipoEnvio,
                               Model model, Principal principal) {

        System.out.println("igualDireccionEnvio es: " + igualDireccionEnvio + "  tipoEnvio: " + tipoEnvio);
        Usuario usuario = usuarioServiceObj.findByUsername(principal.getName());
        CarritoDeCompras carritoDeCompras = usuario.getCarritoDeCompras();
        List<CartItem> cartItemList = carritoDeCompras.getCartItemList();

        if (igualDireccionEnvio.equalsIgnoreCase("true")) {
            billingsEnvio.setPaisBilling(direccionesEnvio.getPais());
            billingsEnvio.setNombreBilling(direccionesEnvio.getNombreReceptor());
            billingsEnvio.setDireccionUnoBilling(direccionesEnvio.getDireccionUno());
            billingsEnvio.setDireccionDosBilling(direccionesEnvio.getDireccionDos());
            billingsEnvio.setDepartamentoBilling(direccionesEnvio.getDepartamento());
            billingsEnvio.setCiudadBilling(direccionesEnvio.getCiudad());
        }

        Date fechaOrden = new Date();
        LocalDate tiempoEstimadoEntrega;

        if (tipoEnvio.equalsIgnoreCase("estandar")) {
            tiempoEstimadoEntrega = LocalDate.now().plusDays(5);
        }
        tiempoEstimadoEntrega = LocalDate.now().plusDays(3);

        Date fechaEntrega = Date.from(tiempoEstimadoEntrega.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Orden orden = ordenServiceObj.crearOrden(fechaOrden,fechaEntrega,tipoEnvio,direccionesEnvio,billingsEnvio,
               pago,usuario,carritoDeCompras);

        model.addAttribute("fechaEntrega", tiempoEstimadoEntrega);
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("orden", orden);



        carritoDeComprasServiceObj.limpiarCarrito(carritoDeCompras);

        //mailSender.send(mailConstructor.constructOrderConfirmationEmail(usuario,orden, Locale.ENGLISH));

        return "ordenCompletada";
    }


}
