package com.example.andre.desafio_cryptography_backend_br.excepetion;

public class UserHasNullRequiredAttributeException extends RuntimeException{
    public UserHasNullRequiredAttributeException(String message){
        super(message);
    }
}
