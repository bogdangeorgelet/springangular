package com.example.springangularapp.repository;


import com.example.springangularapp.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAll();

    Client findByCnp(String email);

}