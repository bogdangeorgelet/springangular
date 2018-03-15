package com.example.springangularapp.repository;


import com.example.springangularapp.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {
  
}