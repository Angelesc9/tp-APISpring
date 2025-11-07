package com.utn.productos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class ActualizarStockDTO {
    @NotNull
    @NotBlank
    @Min(value=0)
    private Integer stock;

}
