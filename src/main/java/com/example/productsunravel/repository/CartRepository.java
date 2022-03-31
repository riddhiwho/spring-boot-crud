package com.example.productsunravel.repository;

import java.util.Optional;

import com.example.productsunravel.model.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
    
    @Query(value = "SELECT c FROM Cart AS c where c.isActive = :isActive") 
    public Cart findByUserIdAndIsActive( @Param("isActive") final boolean isActive);

    
}
