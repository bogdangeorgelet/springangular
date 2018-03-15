package com.example.springangularapp.springangular;


import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {
  
}