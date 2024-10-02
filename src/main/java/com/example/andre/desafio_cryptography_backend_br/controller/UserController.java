package com.example.andre.desafio_cryptography_backend_br.controller;

import com.example.andre.desafio_cryptography_backend_br.dto.UserDTO;
import com.example.andre.desafio_cryptography_backend_br.entity.User;
import com.example.andre.desafio_cryptography_backend_br.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return new ResponseEntity<>(userService.createUser(userDTO),HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
