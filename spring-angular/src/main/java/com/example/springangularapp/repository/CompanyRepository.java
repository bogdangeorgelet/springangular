package com.example.springangularapp.repository;


import com.example.springangularapp.dto.CompanyDto;
import com.example.springangularapp.entity.CompanyEntity;
import org.springframework.data.repository.CrudRepository;


public interface CompanyRepository extends CrudRepository<CompanyEntity, Integer> {

    CompanyDto findByEmail(String email);

}