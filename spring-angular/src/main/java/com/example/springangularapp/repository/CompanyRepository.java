package com.example.springangularapp.repository;


import com.example.springangularapp.entity.Company;
import org.springframework.data.repository.CrudRepository;


public interface CompanyRepository extends CrudRepository<Company, Long> {
    Company findByEmail(String email);

}