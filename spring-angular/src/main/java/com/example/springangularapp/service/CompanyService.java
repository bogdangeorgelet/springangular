package com.example.springangularapp.service;

import com.example.springangularapp.entity.CompanyEntity;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    CompanyEntity save(CompanyEntity companyEntity);

    CompanyEntity findByEmail(String email);

    CompanyEntity findByEmailAndPassword(String email, String password);

    List<CompanyEntity> getAllCompanies();

    Optional<CompanyEntity> findCompanyById(int companyId);

    CompanyEntity findByConfirmationToken(String confirmationToken);
}
