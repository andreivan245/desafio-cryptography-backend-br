package com.example.andre.desafio_cryptography_backend_br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptographyRepository extends JpaRepository<,ID> {
}