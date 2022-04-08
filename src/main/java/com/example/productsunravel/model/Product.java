package com.example.productsunravel.model;
import java.io.Serializable;
import javax.persistence.Entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

@Entity
@Table(name="products")
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, updatable = true)
    private String name;
    
    @Column(nullable=false, updatable = true)
    private float price;

    @Column(nullable=false, updatable = true)
    private String category;

    @Column(nullable=false, updatable = true)
    private Integer count;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count){
        this.count = count;
    }

    public Product(){}
    
    public Product(String name, float price, String category, Integer count){
        this.name=name;
        this.price=price;
        this.category=category;
        this.count=count;
    }

}
