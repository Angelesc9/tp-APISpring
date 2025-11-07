package com.utn.productos.controller;

import com.utn.productos.dto.ActualizarStockDTO; // Asegúrate de importar tu DTO
import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import com.utn.productos.service.ProductoServicio;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos") // Corregido según PDF
public class ProductoController {

    private final ProductoServicio productoServicio;

    // Inyección de dependencias por constructor
    public ProductoController(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodosProductos(){
        List<Producto> productos = productoServicio.obtenerTodos();
        List<ProductoResponseDTO> productosDTO = new ArrayList<ProductoResponseDTO>();
        for (Producto producto : productos) {
            productosDTO.add(productoServicio.crearProductoResponseDTO(producto));
        }
        return ResponseEntity.ok(productosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerProductosPorId(@PathVariable Long id){
        Optional<Producto> productoOptional = productoServicio.obtenerPorId(id);

        if (productoOptional.isPresent()) {
            ProductoResponseDTO productoDTO = productoServicio.crearProductoResponseDTO(productoOptional.get());
            return ResponseEntity.ok(productoDTO);
        } else {
            // Devuelve 404 Not Found si no existe
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductosPorCategoria(@PathVariable("categoria") Categoria categoria) {
        List<Producto> productos = productoServicio.obtenerPorCategoria(categoria);
        List<ProductoResponseDTO>  productosDTO = new ArrayList<ProductoResponseDTO>();
        for (Producto producto : productos) {
            productosDTO.add(productoServicio.crearProductoResponseDTO(producto));
        }
        return ResponseEntity.ok(productosDTO);
    }

    @PostMapping()
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        Producto producto = productoServicio.crearProductoDesdeProductoDTO(productoDTO);
        Producto productoGuardado = productoServicio.crearProducto(producto);
        ProductoResponseDTO responseDTO = productoServicio.crearProductoResponseDTO(productoGuardado);

        // Devuelve 201 Created
        URI location = URI.create("/api/productos/" + productoGuardado.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO){
        Optional<Producto> productoOptional = productoServicio.obtenerPorId(id);

        if (productoOptional.isEmpty()){
            // Devuelve 404 Not Found si no existe
            return ResponseEntity.notFound().build();
        }

        Producto producto = productoServicio.crearProductoDesdeProductoDTO(productoDTO);
        producto.setId(id);

        Producto productoActualizado = productoServicio.actualizarProducto(id, producto);
        ProductoResponseDTO responseDTO = productoServicio.crearProductoResponseDTO(productoActualizado);

        // Devuelve 200 OK con el DTO actualizado
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{id}/stock") // Corregido según PDF
    public ResponseEntity<ProductoResponseDTO> actualizarStock(@PathVariable Long id, @Valid @RequestBody ActualizarStockDTO stockDTO){
        Optional<Producto> productoOptional = productoServicio.obtenerPorId(id);

        if (productoOptional.isEmpty()){
            // Devuelve 404 Not Found si no existe
            return ResponseEntity.notFound().build();
        }

        // Asumo que tu DTO tiene un getter .getStock() [cite: 118]
        Producto productoActualizado = productoServicio.actualizarStock(id, stockDTO.getStock());
        ProductoResponseDTO responseDTO = productoServicio.crearProductoResponseDTO(productoActualizado);

        // Devuelve 200 OK con el DTO actualizado
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        Optional<Producto> productoOptional = productoServicio.obtenerPorId(id);

        if (productoOptional.isEmpty()){
            // Devuelve 404 Not Found si no existe
            return ResponseEntity.notFound().build();
        }
        productoServicio.eliminarProducto(id);

        // Devuelve 204 No Content
        return ResponseEntity.noContent().build();
    }
}