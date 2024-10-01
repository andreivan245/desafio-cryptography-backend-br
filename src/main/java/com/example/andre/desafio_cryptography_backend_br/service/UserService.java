package com.example.andre.desafio_cryptography_backend_br.service;

import com.example.andre.desafio_cryptography_backend_br.dto.UserDTO;
import com.example.andre.desafio_cryptography_backend_br.entity.User;
import com.example.andre.desafio_cryptography_backend_br.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDTO userDTO) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return userRepository.save(encryptUser(userDTO));
    }

    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
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

        String userDocument = new String(userDocumentDigest,StandardCharsets.UTF_8);
        String creditCardToken = new String(creditCardTokenDigest,StandardCharsets.UTF_8);

        return new User(userDocument,creditCardToken,userDTO.getValue());
    }

}
