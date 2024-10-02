package com.example.andre.desafio_cryptography_backend_br.service;

import com.example.andre.desafio_cryptography_backend_br.dto.UserDTO;
import com.example.andre.desafio_cryptography_backend_br.entity.User;
import com.example.andre.desafio_cryptography_backend_br.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDTO userDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userRepository.save(encryptUser(userDTO));
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found: " + id));
    }

    public User updateUser(Long id, UserDTO userDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found: " + id));
        User encryptedUser = encryptUser(userDTO);

        existingUser.setUserDocument(encryptedUser.getUserDocument());
        existingUser.setCreditCardToken(encryptedUser.getCreditCardToken());
        existingUser.setValue(encryptedUser.getValue());

        return userRepository.save(encryptedUser);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public User encryptUser(UserDTO userDTO) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-512");

        byte[] userDocumentDigest = algorithm.digest(userDTO.getUserDocument().getBytes(StandardCharsets.UTF_8));
        byte[] creditCardTokenDigest = algorithm.digest(userDTO.getCreditCardToken().getBytes(StandardCharsets.UTF_8));


        BigInteger userDocumentIntoSignalRepresentation = new BigInteger(1, userDocumentDigest);
        BigInteger creditCardTokenIntoSignalRepresentation = new BigInteger(1, creditCardTokenDigest);


        String userDocumentHashText = userDocumentIntoSignalRepresentation.toString(16);
        String creditCardTokentHashText = creditCardTokenIntoSignalRepresentation.toString(16);

        while (userDocumentHashText.length() < 32 || creditCardTokentHashText.length() < 32) {
            if(userDocumentHashText.length() < 32)
                userDocumentHashText = "0" + userDocumentHashText;

            if(creditCardTokentHashText.length() < 32)
                creditCardTokentHashText = "0" + creditCardTokentHashText;
        }


        return new User(userDocumentHashText,creditCardTokentHashText,userDTO.getValue());
    }

}
