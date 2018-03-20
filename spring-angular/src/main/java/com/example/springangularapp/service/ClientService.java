package com.example.springangularapp.service;

import com.example.springangularapp.entity.ClientEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ClientService {
    List<ClientEntity> findAllClients();
    Optional<ClientEntity> findById(int id);
    ClientEntity findByCnp(String email);
    void saveClient(ClientEntity clientEntity);
}
