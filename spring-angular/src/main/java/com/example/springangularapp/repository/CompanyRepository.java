package com.example.springangularapp.repository;


import com.example.springangularapp.entity.CompanyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CompanyRepository extends CrudRepository<CompanyEntity, Integer> {

    CompanyEntity findByEmail(String email);

    List<CompanyEntity> findAll();

    CompanyEntity findByConfirmationToken(String confirmationToken);

    CompanyEntity findByEmailAndPassword(String email, String password);

}