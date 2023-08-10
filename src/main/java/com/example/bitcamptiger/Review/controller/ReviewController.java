package com.example.bitcamptiger.Review.controller;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<Review> getReviews() {
        return reviewService.getReviews();
    }

    @PostMapping
    public Review createReview(@RequestBody ReviewDto reviewDto, MultipartFile file) throws IOException {

        return reviewService.createReview(reviewDto);
    }

    @PutMapping("/{reviewNum}")
    public Review updateReview(@PathVariable Long reviewNum, @RequestBody ReviewDto reviewDto) {
        return reviewService.updateReview(reviewNum, reviewDto);
    }

    @DeleteMapping("/{reviewNum}")
    public void deleteReview(@PathVariable Long reviewNum) {
        reviewService.deleteReview(reviewNum);
    }
}