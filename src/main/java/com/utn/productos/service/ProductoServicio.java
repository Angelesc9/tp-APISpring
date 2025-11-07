package com.utn.productos.service;

import com.utn.productos.dto.ProductoDTO;
import com.utn.productos.dto.ProductoResponseDTO;
import org.modelmapper.ModelMapper;
import com.utn.productos.dto.ActualizarStockDTO;
import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import com.utn.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio {
    // Inyeccion de dependencias
    private final ProductoRepository productoRepository;
    private final ModelMapper mapper;


    public ProductoServicio(ProductoRepository productoRepository, ModelMapper mapper) {
        this.productoRepository = productoRepository;
        this.mapper = mapper;
    }

    // Metodos
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }
    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> obtenerPorCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public void actualizarProducto(Long id,Producto productoActualizado ){
        if (id != null && productoActualizado != null){
            if( obtenerPorId(id) != null ){
                    eliminarProducto(id);
                    productoActualizado.setId(id);
                    productoRepository.save(productoActualizado);
                }
           //FALTA PONER LA EXCEPCION
        }
    }
    public void actualizarStock(Long id, Integer nuevoStock) {
        if (id !=null && obtenerPorId(id) != null){
            Producto producto = obtenerPorId(id).get();
            producto.setStock(nuevoStock);
            productoRepository.save(producto);
        }
    }
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    //public ActualizarStockDTO crearActualizarStockDTO(Producto producto) {
    //        ActualizarStockDTO actualizarStockDTO = this.mapper.map(producto, ActualizarStockDTO.class);
    //        return actualizarStockDTO;
    //}

    public ProductoDTO crearProductoDTO(Producto producto) {
        ProductoDTO productoDTO = this.mapper.map(producto, ProductoDTO.class);
        return productoDTO;
    }

    public Producto crearProductoDesdeProductoDTO(ProductoDTO productoDTO) {
        Producto producto = this.mapper.map(productoDTO, Producto.class);
        return producto;
    }
    public ProductoResponseDTO crearProductoResponseDTO(Producto producto){
        ProductoResponseDTO productoResponseDTO = this.mapper.map(producto, ProductoResponseDTO.class);
        return productoResponseDTO;
    }

}
