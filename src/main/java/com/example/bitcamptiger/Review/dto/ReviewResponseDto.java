package com.example.bitcamptiger.Review.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDto {
    private Long review_num;
    private String review_content;
    private int review_score;
    private LocalDateTime review_reg_date;
    private List<ReviewFileDto> reviewFileList;

    public ReviewResponseDto(ReviewDto reviewDto, List<ReviewFileDto> reviewFileList) {
        this.review_num = reviewDto.getReviewNum();
        this.review_content = reviewDto.getReviewContent();
        this.review_score = reviewDto.getReviewScore();
        this.review_reg_date = reviewDto.getReviewRegDate();
        this.reviewFileList = reviewFileList;
    }
}
