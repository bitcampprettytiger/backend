package com.example.bitcamptiger.Review.service.impl;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.repository.ReviewRepository;
import com.example.bitcamptiger.Review.service.ReviewService;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<ReviewDto> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(Review::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Review createReview(ReviewDto reviewDto) {
        if (reviewDto == null) {
            throw new IllegalArgumentException("ReviewDto must not be null");
        }

        // Member 검색
        Member member = memberRepository.findById(reviewDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Review review = reviewDto.toEntity();
        review.setMember(member);
        review.setRegDate(LocalDateTime.now());

        try {
            return reviewRepository.save(review);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create review", e);
        }
    }

    @Override
    @Transactional
    public Review updateReview(Long reviewNum, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewNum)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        review.updateReview(reviewDto.getReviewContent(), reviewDto.getScore());

        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewNum) {
        Review review = reviewRepository.findByReviewNum(reviewNum)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        reviewRepository.delete(review);
    }
}
