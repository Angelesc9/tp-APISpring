package com.utn.productos.dto;

import com.utn.productos.model.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ProductoDTO {

    @Schema(description = "nombre del producto", example = "azucar")
    @NotBlank
    @Size(min = 3, max = 100)
    private String nombre;

    @Schema(description = "descripcion del producto",example = "alimento")
    @Size(max = 500)
    private String descripcion;

    @Schema(description = "precio del producto", example = "$150")
    @NonNull
    @DecimalMin(value = "0.01", message = "El precio minimo es 0.01")
    private Double precio;

    @Schema(description = "stock del producto",example = "3")
    @NonNull
    @Min(value = 0)
    private int stock;

    @Schema(description = "Categoria del producto")
    @NonNull
    private Categoria categoria;

}
