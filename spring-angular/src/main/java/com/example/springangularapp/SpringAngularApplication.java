package com.example.springangularapp;

import com.example.springangularapp.entity.Client;
import com.example.springangularapp.entity.Company;
import com.example.springangularapp.entity.Review;
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
        Company company = new Company("nameCompany", "email@company.ro", "passhash");
        Set reviews = new HashSet<Review>() {{
            add(new Review("ceas", 4.0, company));
            add(new Review("slab", 1.0, company));
        }};


        Set clients = new HashSet<Client>() {{
            add(new Client("petrica", "cnp", "address", company));
            add(new Client("petrica", "cnp", "address", company));
            add(new Client("petrica", "cnp", "address", company));
            add(new Client("petrica", "cnp", "address", company));
            add(new Client("petrica", "cnp", "address", company));
        }};
        System.out.println("clients size: " + clients.size());
        company.setClients(clients);
        company.setReviews(reviews);
        companyRepository.save(company);

        Company company1 = new Company("nameCompany1", "email@compan1y.ro", "passhash1");
        Set clients1 = new HashSet<Client>() {{
            add(new Client("petrica1", "cnp", "address", company1));
            add(new Client("petrica1", "cnp", "address", company1));
            add(new Client("petrica1", "cnp", "address", company1));
            add(new Client("petrica1", "cnp", "address", company1));
            add(new Client("petrica1", "cnp", "address", company1));
        }};
        System.out.println("clients size: " + clients.size());
        company1.setClients(clients1);
        companyRepository.save(company1);
    }
}
