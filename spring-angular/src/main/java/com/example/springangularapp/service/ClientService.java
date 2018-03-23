package com.example.springangularapp.service;

import com.example.springangularapp.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ClientService {
    Page<ClientEntity> findAllClients(Pageable pageable);
    Optional<ClientEntity> findById(int id);
    ClientEntity findByCnp(String email);
    void saveClient(ClientEntity clientEntity);
}
