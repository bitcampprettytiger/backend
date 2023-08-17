package com.example.bitcamptiger.Review.service;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;

import java.util.List;

public interface ReviewService {

    Review getReview(Long reviewNum);

    void createReview(Review review, List<ReviewFile> uploadFileList);

    void updateReview(Review review, List<ReviewFile> ufileList);

    void deleteReview(Long reviewNum);

    List<ReviewFile> getReviewFileList(Long reviewNum);

    List<Review> getReviewList();
}
