package com.example.springangularapp.repository;


import com.example.springangularapp.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<ClientEntity, Integer> {
    List<ClientEntity> findAll();

    List<ClientEntity> findClientsByCompanyEntityId(int Id);

    ClientEntity findByCnp(String email);


}