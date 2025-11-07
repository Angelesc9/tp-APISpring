package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductoResponseDTO {
    @Schema(description = "Id del producto")
    private Long id;
    @Schema(description = "nombre del producto")
    private String nombre;
    @Schema(description = "Descripcion del producto")
    private String descripcion;
    @Schema(description = "Precio del producto")
    private Double precio;
    @Schema(description = "Stock del producto")
    private Integer stock;
    @Schema(description = "Categoria del producto")
    private Categoria categoria;
}
