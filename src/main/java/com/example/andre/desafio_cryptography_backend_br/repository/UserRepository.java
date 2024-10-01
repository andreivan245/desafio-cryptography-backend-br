package com.example.andre.desafio_cryptography_backend_br.repository;

import com.example.andre.desafio_cryptography_backend_br.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}