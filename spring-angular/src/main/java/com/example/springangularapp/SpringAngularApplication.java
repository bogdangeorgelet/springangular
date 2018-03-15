package com.example.springangularapp;

import com.example.springangularapp.entity.Client;
import com.example.springangularapp.entity.Company;
import com.example.springangularapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EntityScan
@SpringBootApplication
public class SpringAngularApplication implements CommandLineRunner {
    @Autowired
    ClientRepository clientRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringAngularApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        clientRepository.save(new Client("petrica","cnp","address"));
        clientRepository.save(new Client("petrica","cnp","address"));
        clientRepository.save(new Client("petrica","cnp","address"));
        clientRepository.save(new Client("petrica","cnp","address"));
        clientRepository.save(new Client("petrica","cnp","address"));
    }
}
