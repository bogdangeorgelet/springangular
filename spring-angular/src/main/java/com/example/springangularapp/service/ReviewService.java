package com.example.springangularapp.service;

import com.example.springangularapp.entity.ReviewEntity;

import java.util.List;

public interface ReviewService {
    List<ReviewEntity> findReviewsByCompanyEntityId(int companyId);

    List<ReviewEntity> findReviewsByClientId(int clientId);

    List<ReviewEntity> getAllReviews();

    void saveReview(ReviewEntity reviewEntity);


}
