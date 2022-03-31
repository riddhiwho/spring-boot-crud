package com.example.productsunravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.productsunravel.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

}


