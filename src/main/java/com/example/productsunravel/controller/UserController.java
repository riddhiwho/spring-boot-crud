package com.example.productsunravel.controller;

import javax.validation.Valid;

import com.example.productsunravel.exception.ResourceNotFoundException;
import com.example.productsunravel.model.User;
import com.example.productsunravel.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired UserRepository userRepository;

    @PostMapping("/create")
    public User createUser(@Valid @RequestBody User user) {
    return userRepository.save(user);
    }

    @GetMapping("/getuser/{id}")
    public User getUserById(@PathVariable(value = "id") Integer userId) {
    return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @GetMapping(value = "/getallusers")
    Page<User> userPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @PutMapping("/updateuser/{id}")
    public ResponseEntity<User> updatedUser(
    @PathVariable(value = "id") Integer userId,
    @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("Product", "id", userId));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());

        User updatedUser = userRepository.save(user);
    return ResponseEntity.ok(updatedUser);
}

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<Integer> deleteNote(@PathVariable(value = "id") Integer userId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", userId));

    userRepository.delete(user);

    return ResponseEntity.ok().build();
    }
}


