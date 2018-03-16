package com.example.springangularapp.repository;


import com.example.springangularapp.entity.Client;
import com.example.springangularapp.entity.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ReviewRepository extends CrudRepository<Review, Integer> {
    List<Review> findReviewsByCompanyId(int Id);
    List<Review> findAll();
    List<Review> findReviewsByClientId(int Id);
}