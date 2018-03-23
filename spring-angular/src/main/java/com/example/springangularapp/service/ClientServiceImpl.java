package com.example.springangularapp.service;

import com.example.springangularapp.entity.ClientEntity;
import com.example.springangularapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Page<ClientEntity> findAllClients(org.springframework.data.domain.Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Optional<ClientEntity> findById(int id) {
        return clientRepository.findById(id);
    }

    @Override
    public ClientEntity findByCnp(String cnp) {
        return clientRepository.findByCnp(cnp);
    }

    @Override
    public void saveClient(ClientEntity clientEntity) {
        clientRepository.save(clientEntity);
    }
}
