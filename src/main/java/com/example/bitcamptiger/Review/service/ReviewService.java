package com.example.bitcamptiger.Review.service;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.entity.Review;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewDto reviewDto);

    Review updateReview(Long reviewNum, ReviewDto reviewDto);

    void deleteReview(Long reviewNum);

    List<Review> getReviews();
}
