package com.example.springangularapp.service;

import com.example.springangularapp.entity.CompanyEntity;
import com.example.springangularapp.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public CompanyEntity save(CompanyEntity companyEntity) {
//        companyEntity.setPassword(bCryptPasswordEncoder.encode(companyEntity.getPassword()));
        return companyRepository.save(companyEntity);
    }

    @Override
    public CompanyEntity findByEmail(String email) {
        return companyRepository.findByEmail(email);
    }

    @Override
    public CompanyEntity findByEmailAndPassword(String email, String password) {
        return companyRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public List<CompanyEntity> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<CompanyEntity> findCompanyById(int companyId) {
        return companyRepository.findById(companyId);
    }

    @Override
    public CompanyEntity findByConfirmationToken(String confirmationToken) {
        return companyRepository.findByConfirmationToken(confirmationToken);
    }

}
