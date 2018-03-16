package com.example.springangularapp;

import com.example.springangularapp.dto.CompanyDto;
import com.example.springangularapp.entity.ClientEntity;
import com.example.springangularapp.entity.CompanyEntity;
import com.example.springangularapp.entity.ReviewEntity;
import com.example.springangularapp.repository.ClientRepository;
import com.example.springangularapp.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashSet;
import java.util.Set;

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
//        CompanyDto companyEntity = new CompanyDto("nameCompany", "email@companyEntity.ro", "passhash");
//        ClientEntity a = new ClientEntity("petrica care a dat review", "cnp", "address", companyEntity);
//        Set reviews = new HashSet<ReviewEntity>() {{
//            add(new ReviewEntity("ceas", 4.0, companyEntity, a));
//            add(new ReviewEntity("slab", 1.0, companyEntity, a));
//        }};
//
//
//        Set clients = new HashSet<ClientEntity>() {{
//            add(a);
//            add(new ClientEntity("petrica", "cnp", "address", companyEntity));
//            add(new ClientEntity("petrica", "cnp", "address", companyEntity));
//            add(new ClientEntity("petrica", "cnp", "address", companyEntity));
//            add(new ClientEntity("petrica", "cnp", "address", companyEntity));
//        }};
//        System.out.println("clients size: " + clients.size());
//        companyRepository.save(companyEntity);

//        CompanyEntity companyEntity1 = new CompanyEntity("nameCompany1", "email@compan1y.ro", "passhash1");
//        Set clients1 = new HashSet<ClientEntity>() {{
//            add(new ClientEntity("petrica1", "cnp", "address", companyEntity1));
//            add(new ClientEntity("petrica1", "cnp", "address", companyEntity1));
//            add(new ClientEntity("petrica1", "cnp", "address", companyEntity1));
//            add(new ClientEntity("petrica1", "cnp", "address", companyEntity1));
//            add(new ClientEntity("petrica1", "cnp", "address", companyEntity1));
//        }};
//        companyRepository.save(companyEntity1);
    }
}
