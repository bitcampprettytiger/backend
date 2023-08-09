package com.example.bitcamptiger.Review.service.impl;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.repository.ReviewRepository;
import com.example.bitcamptiger.Review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }
    @Override
    @Transactional
    public Review createReview(ReviewDto reviewDto) {
        Review review = Review.builder()
                .orderNum(reviewDto.getOrderNum())
                .storeId(reviewDto.getStoreId())
                .reviewContent(reviewDto.getReviewContent())
                .score(reviewDto.getScore())
                .regDate(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long reviewNum, ReviewDto reviewDto) {
        Review existingReview =reviewRepository.findById(reviewNum).orElse(null);
        if(existingReview != null) {
            existingReview.setReviewContent(reviewDto.getReviewContent());
            existingReview.setScore(reviewDto.getScore());
        }
        return null;
    }

    @Override
    public void deleteReview(Long reviewNum) {
        reviewRepository.deleteById(reviewNum);
    }
}
