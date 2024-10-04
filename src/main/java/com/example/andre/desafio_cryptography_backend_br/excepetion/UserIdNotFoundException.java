package com.example.andre.desafio_cryptography_backend_br.excepetion;

public class UserIdNotFoundException extends RuntimeException{
    public UserIdNotFoundException(String message){
        super(message);
    }
}
