package com.utn.productos.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Getter
@Setter
@RestController
public class ErrorResponse {

    private Date timestamp;
    private Integer httpStatus;
    private String errorMessage;
    private String route;

}
