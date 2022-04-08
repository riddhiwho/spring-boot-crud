package com.example.productsunravel.controller;

import com.example.productsunravel.model.Cart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.productsunravel.common.ApiResponse;
import com.example.productsunravel.model.Product;
import com.example.productsunravel.model.User;
import com.example.productsunravel.repository.CartRepository;
import com.example.productsunravel.repository.ProductRepository;
import com.example.productsunravel.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequestMapping("/cart")
public class CartController {

    // @Autowired
    CartRepository cartRepository;
    // @Autowired
    ProductRepository productRepository;
    // @Autowired
    UserRepository userRepository;

    @Autowired
    public CartController(ProductRepository productRepository, CartRepository cartRepository, UserRepository userRepository){
        this.productRepository=productRepository;
        this.cartRepository=cartRepository;
        this.userRepository=userRepository;
    }

    public void addToCart(Cartreq cartreq, Optional<Product> product, Optional<User> user){
        Cart cart = new Cart();
        cart.setIsActive(true);
        cart.setProduct(product.get());
        cart.setQuantity(cartreq.getQty());
        cart.setUser(user.get());
        cart.setCreatedAt(new Date());
        cartRepository.save(cart);
    }
   


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@Valid @RequestBody Cartreq cartreq) {

        try{
            Optional<Product> product = productRepository.findById(cartreq.getProductId());
            // System.out.println(product.isPresent());
            Optional<User> user = userRepository.findById(cartreq.getUserId());
            if(product.get().getCount()>=cartreq.getQty()){
                System.out.println("prod available");
                addToCart(cartreq, product, user);
            }else{
                System.out.println("prod not available");
            }
        }catch(NullPointerException e){
            System.out.println("NULLLLL POINTER EXCEPTION");
        }


        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }


    @PatchMapping("/update/{id}/{quantity}")
public ResponseEntity<ApiResponse> updateCart(@PathVariable(value="id") Integer cartId, @PathVariable(value="quantity") int quantity) {
	try {
		Cart cart = cartRepository.findById(cartId).get();
		cart.setQuantity(quantity);
        Long productId = cart.getProduct().getId();
        Product p = productRepository.findById(productId).get();
        if(p.getCount()-quantity>=0){
            cartRepository.save(cart);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "cart updated"), HttpStatus.OK);
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product not available"), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

    @GetMapping("/fetch/{id}")
    public List<Cart> getCartById(@PathVariable(value = "id") Integer userId) {
        try{
            return cartRepository.findByUserId(userId);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable(value="id") Integer cartId){
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(cart==null){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "ID does not exist"), HttpStatus.OK);
        }
        cartRepository.delete(cart.get());
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Deleted from cart"), HttpStatus.OK);
    }
    
    
}
