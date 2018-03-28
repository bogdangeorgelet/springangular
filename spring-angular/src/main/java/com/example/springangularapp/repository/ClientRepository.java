package com.example.springangularapp.repository;


import com.example.springangularapp.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {
    @Override
    Page<ClientEntity> findAll(Pageable pageable);

    ClientEntity findByCnp(String cnp);


}