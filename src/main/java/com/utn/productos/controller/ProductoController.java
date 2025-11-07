package com.utn.productos.controller;

import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.model.Producto;
import com.utn.productos.service.ProductoServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoServicio productoServicio;

    // Inyección de dependencias por constructor
    public ProductoController(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }
@GetMapping
    public List<ProductoResponseDTO> obtenerTodosProductos(){
        List<Producto> productos = productoServicio.obtenerTodos();
        List<ProductoResponseDTO> productosDTO = new ArrayList<ProductoResponseDTO>();
    for (Producto producto : productos
         ) {
        productosDTO.add(productoServicio.crearProductoResponseDTO(producto));
    }
    return productosDTO;
}

    @GetMapping("/id")
    public ProductoResponseDTO obtenerProductosPorId(Long id){
        Producto producto = productoServicio.obtenerPorId(id).get();
        ProductoResponseDTO productoDTO = new ProductoResponseDTO();
        if (producto != null){
        productoDTO = productoServicio.crearProductoResponseDTO(producto);
        }
        else {
            //LANZAR EXCEPCION DE NO ENCONTRADO
        }
        return productoDTO;
    }
}
