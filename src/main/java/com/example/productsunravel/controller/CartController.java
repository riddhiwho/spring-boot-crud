package com.example.productsunravel.controller;

import com.example.productsunravel.model.Cart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.productsunravel.common.ApiResponse;
// import com.example.productsunravel.dto.cart.AddToCartDto;
import com.example.productsunravel.exception.ResourceNotFoundException;
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

    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    // public void addToCart(Cartreq cartreq, Product product, User user){
    //     Cart cart = new Cart(product, cartreq.getQty(), user);
    //     cartRepository.save(cart);
    // }

    // private void addToCart(@Valid Cartreq cartreq, Optional<Product> product, Optional<User> user) {
    //     // Cart cart = new Cart(product.get(), cartreq.getQty(), user.get());

    //     cartRepository.save(cart);
    // }
    public void addToCart(Cartreq cartreq, Optional<Product> product, Optional<User> user){
        Cart cart = new Cart();
        cart.setIsActive(true);
        cart.setProduct(product.get());
        cart.setQuantity(cartreq.getQty());
        cart.setUser(user.get());
        cart.setCreatedAt(new Date());
        cartRepository.save(cart);
    }
    // @Autowired
    // public CartController(ProductRepository productRepository, CartRepository cartRepository, UserRepository userRepository){
    //     this.productRepository=productRepository;
    //     this.cartRepository=cartRepository;
    //     this.userRepository=userRepository;
    // }
    // private static void addToCart(@Valid Cartreq cartreq, Optional<Product> product, Optional<User> user) {
    //     Cart cart = new Cart(product.get(), cartreq.getQty(), user.get());
    //     cartRepository.save(cart);
    // }




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

//    @GetMapping("/fetch")
//    public ResponseEntity<ApiResponse> fetchFromCart(@Valid @RequestParam Long productId){

//    }
    @GetMapping("/fetch/{id}")
    public List<Cart> getCartById(@PathVariable(value = "id") Integer userId) {
        try{
            return cartRepository.findByUserId(userId);
        }catch(Exception e){
            e.printStackTrace();
        }
    // return cartRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", userId));
        return null;
    }
    
// @GetMapping("/products/{id}")
// public Product getProductById(@PathVariable(value = "id") Long productId) {
//     return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
// }
















//     @PutMapping("/add")
//     public void addToCart(@Valid @RequestBody Cartreq cartreq) {
//         if(cartreq==null){
//             System.out.println("*****CARTREQ IS NULLLL*******");
//         }else{
//         Product product = productRepository.findById(cartreq.getProductId()).get();
//         if (product.getCount()>=cartreq.getQty()){
//             System.out.println("product available");
//         }else{
//             System.out.println("product unavailable");
//         }
//         // // Cart cart = cartRepository.
//         // // find cart which has a user id() and isActive=true;
//         // Cart cart = cartRepository.findByUserIdAndIsActive(true);
// //         // System.out.println(cart);
        
// //         }
//         // return ResponseEntity.ok(cart);

//     }
    
    
}
