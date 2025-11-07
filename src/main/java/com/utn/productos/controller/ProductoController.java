package com.utn.productos.controller;

import com.utn.productos.dto.ActualizarStockDTO; // Asegúrate de importar tu DTO
import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import com.utn.productos.service.ProductoServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos") // Corregido según PDF
@Tag(name ="Controladro de Productos")
public class ProductoController {

    private final ProductoServicio productoServicio;

    // Inyección de dependencias por constructor
    public ProductoController(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }
    @Operation(summary = "Obtiene todos los productos")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodosProductos(){
        List<Producto> productos = productoServicio.obtenerTodos();
        List<ProductoResponseDTO> productosDTO = new ArrayList<ProductoResponseDTO>();
        for (Producto producto : productos) {
            productosDTO.add(productoServicio.crearProductoResponseDTO(producto));
        }
        return ResponseEntity.ok(productosDTO);
    }


    @ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @Operation(summary = "Obtiene el producto por su ID")
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

    @ApiResponse(responseCode = "200", description = "Lista de productos filtrada por categoría")
    @ApiResponse(responseCode = "400", description = "Categoría no válida")
    @Operation(summary = "Obtiene todos los productos de una categoría")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerProductosPorCategoria(@PathVariable("categoria") Categoria categoria) {
        List<Producto> productos = productoServicio.obtenerPorCategoria(categoria);
        List<ProductoResponseDTO>  productosDTO = new ArrayList<ProductoResponseDTO>();
        for (Producto producto : productos) {
            productosDTO.add(productoServicio.crearProductoResponseDTO(producto));
        }
        return ResponseEntity.ok(productosDTO);
    }


    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos")
    @Operation(summary = "Crea un nuevo producto")
    @PostMapping()
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO) {
        Producto producto = productoServicio.crearProductoDesdeProductoDTO(productoDTO);
        Producto productoGuardado = productoServicio.crearProducto(producto);
        ProductoResponseDTO responseDTO = productoServicio.crearProductoResponseDTO(productoGuardado);

        // Devuelve 201 Created
        URI location = URI.create("/api/productos/" + productoGuardado.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }


    @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos")
    @Operation(summary = "Actualiza un producto existente")
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


    @ApiResponse(responseCode = "200", description = "Stock del producto actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @ApiResponse(responseCode = "400", description = "Datos de entrada no válidos")
    @Operation(summary = "Se actualiza el stock de un producto")
    @PatchMapping("/{id}/stock")
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

    @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @Operation(summary = "Elimina un producto por su ID")
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