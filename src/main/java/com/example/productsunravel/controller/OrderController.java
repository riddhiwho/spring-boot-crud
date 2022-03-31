package com.example.productsunravel.controller;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import com.example.productsunravel.common.ApiResponse;
import com.example.productsunravel.model.Cart;
import com.example.productsunravel.model.Order;
import com.example.productsunravel.model.Product;
import com.example.productsunravel.model.User;
import com.example.productsunravel.repository.CartRepository;
import com.example.productsunravel.repository.OrderRepository;
import com.example.productsunravel.repository.ProductRepository;
import com.example.productsunravel.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class OrderController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrderRepository orderRepository;

    public Cart buyNow(Cartreq cartreq, Optional<Product> product, Optional<User> user){
        Cart cart = new Cart();
        cart.setIsActive(true);
        cart.setProduct(product.get());
        cart.setQuantity(cartreq.getQty());
        cart.setUser(user.get());
        cart.setCreatedAt(new Date());
        return cartRepository.save(cart);
    }

    @PostMapping("/buynow")
    public ResponseEntity<ApiResponse> buyNow(@Valid @RequestBody Cartreq cartreq) {

        try{
            Optional<Product> product = productRepository.findById(cartreq.getProductId());
            Optional<User> user = userRepository.findById(cartreq.getUserId());
            if(product.get().getCount()>=cartreq.getQty()){
                System.out.println("prod available");
                Cart c = buyNow(cartreq, product, user);
                Optional<Cart> cart = cartRepository.findById(c.getId());
                
                Integer pcount = cart.get().getProduct().getCount();
                if(cart.get().getQuantity()<=pcount){
                    System.out.println("prod available");
                    Product p = productRepository.findById(cart.get().getProduct().getId()).get();
                    p.setCount(p.getCount()-cart.get().getQuantity());
                    productRepository.save(p);
                    Order o = new Order();
                    o.setCart(cart.get());
                    o.setCreatedAt(new Date());
                    o.setTotalAmount(pcount*p.getPrice());
                    orderRepository.save(o);
    
                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "order placed"), HttpStatus.OK);
                }
            }else{
                System.out.println("prod not available");
            }
        }catch(NullPointerException e){
            System.out.println("NULLLLL POINTER EXCEPTION");
        }


        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }
    

    @PostMapping("/product/{id}")
    public ResponseEntity<ApiResponse> addToCart(@Valid @PathVariable(value="id") Integer cartId) {

        try{
            Optional<Cart> cart = cartRepository.findById(cartId);
            Integer pcount = cart.get().getProduct().getCount();
            if(cart.get().getQuantity()<=pcount){
                System.out.println("prod available");
                Product p = productRepository.findById(cart.get().getProduct().getId()).get();
                p.setCount(p.getCount()-cart.get().getQuantity());
                productRepository.save(p);
                Order o = new Order();
                o.setCart(cart.get());
                o.setCreatedAt(new Date());
                o.setTotalAmount(pcount*p.getPrice());
                orderRepository.save(o);

		        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "order placed"), HttpStatus.OK);
            }else{
                System.out.println("prod not available");
            }
        }catch(NullPointerException e){
            System.out.println("NULLLLL POINTER EXCEPTION");
        }


        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }


    @GetMapping("/getorder/{id}")
    public Order getCartById(@PathVariable(value = "id") Integer orderId) {
        try{
            return orderRepository.findById(orderId).get();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
