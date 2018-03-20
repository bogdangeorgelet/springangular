package com.example.springangularapp.service;

import com.example.springangularapp.entity.CompanyEntity;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    void save(CompanyEntity companyEntity);

    CompanyEntity findByCompanyEmail(String username);
    List<CompanyEntity>  getAllCompanies();
    Optional<CompanyEntity> findCompanyById(int companyId);
}
