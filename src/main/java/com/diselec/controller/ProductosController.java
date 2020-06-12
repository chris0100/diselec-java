package com.diselec.controller;

import com.diselec.entities.Producto;
import com.diselec.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
public class ProductosController {

    @Autowired
    ProductoService productoServiceObj;


    @GetMapping("/catalogoProductos")
    public String catalogoProductos(Model model){
        List<Producto> listaProductos = productoServiceObj.listaProductos();
        if (listaProductos.isEmpty()){
            model.addAttribute("listaVacia",true);
        }
        model.addAttribute("listaProductos", listaProductos);
        model.addAttribute("activeAll",true);
        return "catalogoProductos";
    }


    @GetMapping("/detalleProductos")
    public String detalleProductos(@PathParam("id") Long id, @PathParam("categoria")String categoria, Model model){
        Producto producto = productoServiceObj.findById(id);
        System.out.println("categoria: " + categoria);

        if(producto == null){
            System.out.println("retorna a pagina de error");
            return "/index";
        }

        categoria = categoria.replace(" ","");
        String activarCategoria = "active" + categoria;

        model.addAttribute(activarCategoria,true);
        model.addAttribute("producto", producto);
        return "detalleProductos";
    }


    @GetMapping("/buscarCategoria")
    public String buscarCategoria(@RequestParam("categoria") String categoria,
                                  Model model){
        List<Producto> listaPorCategoria = productoServiceObj.findByCategoria(categoria);

        if (listaPorCategoria.isEmpty()){
            model.addAttribute("listaVacia",true);
        }

        categoria = categoria.replace(" ","");
        String activarCategoria = "active" + categoria;
        System.out.println(activarCategoria);

        model.addAttribute(activarCategoria,true);
        model.addAttribute("listaProductos",listaPorCategoria);
        return "catalogoProductos";
    }

}
