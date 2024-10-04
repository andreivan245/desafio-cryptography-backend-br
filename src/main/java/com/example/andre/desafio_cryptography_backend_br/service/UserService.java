package com.example.andre.desafio_cryptography_backend_br.service;

import com.example.andre.desafio_cryptography_backend_br.dto.UserDTO;
import com.example.andre.desafio_cryptography_backend_br.entity.User;
import com.example.andre.desafio_cryptography_backend_br.excepetion.UserIdNotFoundException;
import com.example.andre.desafio_cryptography_backend_br.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDTO userDTO)  {
        return userRepository.save(encryptUser(userDTO));
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserIdNotFoundException("User id not found: " + id));
    }

    public User updateUser(Long id, UserDTO userDTO)  {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new UserIdNotFoundException("User id not found: " + id));

        User encryptedUser = encryptUser(userDTO);

        encryptedUser.setID(existingUser.getID());

        return userRepository.save(encryptedUser);
    }

    public void deleteUser(Long id) {
       userRepository.deleteById(id);
    }

    public User encryptUser(UserDTO userDTO) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");

            byte[] userDocumentDigest = algorithm.digest(userDTO.getUserDocument().getBytes(StandardCharsets.UTF_8));
            byte[] creditCardTokenDigest = algorithm.digest(userDTO.getCreditCardToken().getBytes(StandardCharsets.UTF_8));


            BigInteger userDocumentIntoSignalRepresentation = new BigInteger(1, userDocumentDigest);
            BigInteger creditCardTokenIntoSignalRepresentation = new BigInteger(1, creditCardTokenDigest);


            StringBuilder userDocumentHashText = new StringBuilder(userDocumentIntoSignalRepresentation.toString(16));
            StringBuilder creditCardTokenHashText = new StringBuilder(creditCardTokenIntoSignalRepresentation.toString(16));

            while (userDocumentHashText.length() < 32 || creditCardTokenHashText.length() < 32) {

                if (userDocumentHashText.length() < 32) {
                    userDocumentHashText.insert(0, "0");
                }

                if (creditCardTokenHashText.length() < 32) {
                    creditCardTokenHashText.insert(0, "0");
                }
            }


            return new User(userDocumentHashText.toString(), creditCardTokenHashText.toString(), userDTO.getValue());

        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException(exception);
        }
    }

}
