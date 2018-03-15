package com.example.springangularapp.repository;


import com.example.springangularapp.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepository extends JpaRepository<Company, Long> {

}