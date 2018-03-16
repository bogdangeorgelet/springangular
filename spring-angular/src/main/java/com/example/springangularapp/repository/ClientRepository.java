package com.example.springangularapp.repository;


import com.example.springangularapp.entity.Client;
import com.example.springangularapp.entity.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    List<Client> findAll();

    List<Client> findClientsByCompanyId(int Id);

    Client findByCnp(String email);


}