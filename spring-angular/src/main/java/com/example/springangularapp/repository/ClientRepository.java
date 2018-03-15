package com.example.springangularapp.repository;


import com.example.springangularapp.entity.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    List<Client> findAll();

    Client findByCnp(String email);


}