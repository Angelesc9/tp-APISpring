package com.utn.productos_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.utn.productos", "com.utn.productos_api"})
@EntityScan("com.utn.productos.model")
@EnableJpaRepositories("com.utn.productos.repository")
public class ProductosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductosApiApplication.class, args);
	}

}
