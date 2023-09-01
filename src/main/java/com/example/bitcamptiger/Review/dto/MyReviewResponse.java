package com.example.bitcamptiger.Review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyReviewResponse {
    private List<ReviewDto> reviews;
    private int numberOfReviews;
}