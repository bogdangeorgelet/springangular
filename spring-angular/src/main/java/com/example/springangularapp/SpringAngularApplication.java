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
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EntityScan
@SpringBootApplication
@Import(value = {WebSecurityConfig.class})
public class SpringAngularApplication {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CompanyRepository companyRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringAngularApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        CompanyEntity companyEntity = new CompanyEntity();
//        companyEntity.setName("Fortech.");
//        companyEntity.setEmail("fortech@mail.com");
//        companyEntity.setPassword("123");
//        companyRepository.save(companyEntity);
//        for (int i = 0; i < 10; i++) {
//            ClientEntity clientEntity = new ClientEntity();
//            clientEntity.setAddress("Fasca "+i);
//            clientEntity.setCnp("123"+i);
//            clientEntity.setName("petrica:"+i);
//
//            clientRepository.save(clientEntity);
//        }
//    }
}
