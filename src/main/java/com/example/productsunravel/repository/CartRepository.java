package com.example.productsunravel.repository;

import java.util.List;

import com.example.productsunravel.model.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

    @Query("select  irv from Cart irv join fetch irv.user ir where  ir.id=:id")
    List<Cart> findByUserId(@Param("id") int id);
}
