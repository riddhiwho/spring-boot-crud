package com.example.productsunravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProductsUnravelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsUnravelApplication.class, args);
	}

}
