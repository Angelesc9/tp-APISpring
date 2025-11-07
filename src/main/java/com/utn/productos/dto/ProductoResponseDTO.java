package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private Categoria categoria;
}
