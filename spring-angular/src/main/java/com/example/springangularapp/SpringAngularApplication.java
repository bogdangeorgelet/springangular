package com.example.springangularapp;

import com.example.springangularapp.entity.ClientEntity;
import com.example.springangularapp.entity.CompanyEntity;
import com.example.springangularapp.repository.ClientRepository;
import com.example.springangularapp.repository.CompanyRepository;
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
    @Autowired
    CompanyRepository companyRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringAngularApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setName("Fortech.");
        companyEntity.setEmail("fortech@mail.com");
        companyEntity.setPassword("parolahash");
        companyRepository.save(companyEntity);
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setAddress("Fasca 56");
        clientEntity.setCnp("123");
        clientEntity.setName("petrica");
        clientRepository.save(clientEntity);
    }
}
