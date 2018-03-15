package com.example.springangularapp.repository;


import com.example.springangularapp.entity.Review;
import org.springframework.data.repository.CrudRepository;


public interface ReviewRepository extends CrudRepository<Review, Long> {

}