package com.example.productsunravel.repository;



import java.util.List;

import com.example.productsunravel.model.Product;
import org.springframework.stereotype.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT c.category, SUM(c.count) FROM Product AS c GROUP BY c.category")   
     List<Object[]> countCustomersByType();


    List<Product> findByCategory(String category);
    List<Product> findByPriceBetween (float start, float end);

    List<Product> findByNameContaining(String name);
    List<Product> findByCategoryContaining(String category);
    Product findById(long id);
}