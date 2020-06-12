package com.diselec.controller;


import com.diselec.entities.CarritoDeCompras;
import com.diselec.entities.CartItem;
import com.diselec.entities.Producto;
import com.diselec.entities.Usuario;
import com.diselec.service.CarritoDeComprasService;
import com.diselec.service.CartItemService;
import com.diselec.service.ProductoService;
import com.diselec.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;

@Controller
public class CarritoComprasController {

    @Autowired
    private UsuarioService usuarioServiceObj;

    @Autowired
    private CartItemService cartItemServiceObj;

    @Autowired
    private ProductoService productoServiceObj;

    @Autowired
    private CarritoDeComprasService carritoDeComprasServiceObj;




    @GetMapping("/carritoCompras")
    public String carritoCompras(Model model, Principal principal){
        Usuario usuarioLogueado = usuarioServiceObj.findByUsername(principal.getName());

        //se obtiene el carrito de compras
        CarritoDeCompras carritoDeCompras = usuarioLogueado.getCarritoDeCompras();

        //Se obtiene la lista de los cartItem
        List<CartItem> cartItemList = cartItemServiceObj.findByCarritoDeCompras(carritoDeCompras);

        //actualizar el carrito de compras
        carritoDeComprasServiceObj.actualizaCarritoCompras(carritoDeCompras,cartItemList);

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("carritoDeCompras", carritoDeCompras);

        return "carritoCompras";
    }

    @GetMapping("/preModalCompras")
    public String preModalCompras(@PathParam("id") Long id, Model model){
        Producto producto = productoServiceObj.findById(id);

        List<Producto> listaProductos = productoServiceObj.findByCategoria(producto.getCategoria());
        model.addAttribute("listaProductos", listaProductos);
        model.addAttribute("productoSeleccionado",producto);

        String tabActive = "active" + producto.getCategoria();
        tabActive = tabActive.replace(" ","");
        model.addAttribute(tabActive,true);

        return "modal";
    }



    @PostMapping("/addItemToCarrito")
    public String addItemToCarrito(@ModelAttribute("idProducto")Long id, @ModelAttribute("cantidad") String cantidad,
                                   Model model, Principal principal,RedirectAttributes attributes){

        System.out.println("el id del producto a añadir es: " + id + ": " + cantidad);
        Usuario usuarioLogueado = usuarioServiceObj.findByUsername(principal.getName());
        Producto producto = productoServiceObj.findById(id);


        //si la cantidad solicitada es mayor a la cantidad en stock, envia error.
        if (Integer.parseInt(cantidad) > producto.getCantidad() || Integer.parseInt(cantidad) < 1){
            attributes.addFlashAttribute("error","Error: Revisa los datos ingresados");
            return "redirect:/catalogoProductos";
        }

        CartItem cartItem = cartItemServiceObj.addProductoAlCartItem(producto, usuarioLogueado, Integer.parseInt(cantidad));

        attributes.addFlashAttribute("success","Producto agregado al carrito Satisfactoriamente");

        return "redirect:/catalogoProductos";
    }




    @PostMapping("/actualizarCartItem")
    public String actualizarCartItem(@ModelAttribute("id") Long cartItemId, @ModelAttribute("cantidad") int cantidad,
                                     RedirectAttributes attributes){
        System.out.println("reconoce el id: " + cartItemId + " y la cantidad: " + cantidad);
        CartItem cartItem = cartItemServiceObj.findById(cartItemId);
        cartItem.setCantidad(cantidad);
        cartItemServiceObj.actualizarCartItem(cartItem);
        attributes.addFlashAttribute("success","Actualizado correctamente");
        return "redirect:/carritoCompras";
    }


    @GetMapping("/modal")
    public String modal(){
        return "modal";
    }


    @GetMapping("/quitarCartItem")
    public String quitarCartItem(@PathParam("id")Long id, Model model){
        System.out.println("el id del item es " + id);
        cartItemServiceObj.quitarCartItem(cartItemServiceObj.findById(id));
        return "redirect:/carritoCompras";
    }






}
