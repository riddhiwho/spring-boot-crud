package com.example.productsunravel.repository;

import com.example.productsunravel.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}