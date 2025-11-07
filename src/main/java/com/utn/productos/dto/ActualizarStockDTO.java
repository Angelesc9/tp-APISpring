package com.utn.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "stock del producto", example = "32")
    private Integer stock;

}
