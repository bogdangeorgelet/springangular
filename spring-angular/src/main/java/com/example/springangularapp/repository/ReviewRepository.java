package com.example.springangularapp.repository;


import com.example.springangularapp.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long> {

}