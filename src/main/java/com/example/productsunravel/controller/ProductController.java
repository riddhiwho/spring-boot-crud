package com.example.productsunravel.controller;

import com.example.productsunravel.exception.ResourceNotFoundException;
import com.example.productsunravel.model.Product;
import com.example.productsunravel.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;




@RestController
@RequestMapping("/api")
public class ProductController {
    
@Autowired
ProductRepository productRepository;

// @GetMapping("/products")
// public List<Product> getAllProducts() {
//     return productRepository.findAll();
// }

@GetMapping(value="/status")
public String status(){
    return "ok";
}

@GetMapping(value = "/products")
Page<Product> productPageable(Pageable pageable) {
    return productRepository.findAll(pageable);
}



@PostMapping("/products")
public Product createProduct(@Valid @RequestBody Product product) {
    return productRepository.save(product);
}

@GetMapping("/products/{id}")
public Product getProductById(@PathVariable(value = "id") Long productId) {
    return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
}

// @PutMapping("/products/{id}")
// public Product updateProduct(@PathVariable(value = "id") Long productId, @Valid @RequestBody Product productDetails) {

//     Product product = productRepository.findById(productId)
//             .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

//     product.setName(productDetails.getName());
//     product.setPrice(productDetails.getPrice());
//     product.setCategory(productDetails.getCategory());
//     product.setCount(productDetails.getCount());

//     Product updatedProduct = productRepository.save(product);
//     return updatedProduct;
// }

@PutMapping("/products/{id}")
public ResponseEntity<Product> updatedProduct(
@PathVariable(value = "id") Long productId,
@Valid @RequestBody Product productDetails) throws ResourceNotFoundException {
     Product product = productRepository.findById(productId)
      .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

      product.setName(productDetails.getName());
      product.setPrice(productDetails.getPrice());
      product.setCategory(productDetails.getCategory());
      product.setCount(productDetails.getCount());

     Product updatedProduct = productRepository.save(product);
    return ResponseEntity.ok(updatedProduct);
}

@PatchMapping("/products/{id}/{name}")
public ResponseEntity<Product> updateProductPartially(@PathVariable Long id, @PathVariable String name) {
	try {
		Product product = productRepository.findById(id).get();
		product.setName(name);
		return new ResponseEntity<Product>(productRepository.save(product), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@PatchMapping("/products/category/{id}/{category}")
public ResponseEntity<Product> updateProductCategory(@PathVariable Long id, @PathVariable String category) {
	try {
		Product product = productRepository.findById(id).get();
		product.setCategory(category);
		return new ResponseEntity<Product>(productRepository.save(product), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@PatchMapping("/products/price/{id}/{price}")
public ResponseEntity<Product> updateProductPrice(@PathVariable Long id, @PathVariable float price) {
	try {
		Product product = productRepository.findById(id).get();
		product.setPrice(price);
		return new ResponseEntity<Product>(productRepository.save(product), HttpStatus.OK);
	} catch (Exception e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@DeleteMapping("/products/{id}")
public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long productId) {
    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", productId));

    productRepository.delete(product);

    return ResponseEntity.ok().build();
}


}


