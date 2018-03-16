package com.example.springangularapp.repository;


import com.example.springangularapp.entity.ReviewEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ReviewRepository extends CrudRepository<ReviewEntity, Integer> {
    List<ReviewEntity> findReviewsByCompanyEntityId(int Id);
    List<ReviewEntity> findAll();
    List<ReviewEntity> findReviewsByClientId(int Id);
}