package com.example.springangularapp.service;

import com.example.springangularapp.entity.ReviewEntity;
import com.example.springangularapp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public List<ReviewEntity> findReviewsByCompanyEntityId(int companyId) {
        return reviewRepository.findReviewsByCompanyEntityId(companyId);
    }

    @Override
    public List<ReviewEntity> findReviewsByClientId(int clientId) {
        return reviewRepository.findReviewsByClientId(clientId);
    }

    @Override
    public List<ReviewEntity> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public void saveReview(ReviewEntity reviewEntity) {
        reviewRepository.save(reviewEntity);

    }
}
