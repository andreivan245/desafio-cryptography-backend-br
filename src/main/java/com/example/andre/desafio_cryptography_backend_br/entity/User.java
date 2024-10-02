package com.example.andre.desafio_cryptography_backend_br.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userDocument;

    private String creditCardToken;

    private Long value;

    public User() {
    }

    public User(Long id, String userDocument, String creditCardToken, Long value) {
        this.id = id;
        this.userDocument = userDocument;
        this.creditCardToken = creditCardToken;
        this.value = value;
    }

    public User(String userDocument, String creditCardToken, Long value) {
        this.userDocument = userDocument;
        this.creditCardToken = creditCardToken;
        this.value = value;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public String getUserDocument() {
        return userDocument;
    }

    public void setUserDocument(String userDocument) {
        this.userDocument = userDocument;
    }

    public String getCreditCardToken() {
        return creditCardToken;
    }

    public void setCreditCardToken(String creditCardToken) {
        this.creditCardToken = creditCardToken;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
