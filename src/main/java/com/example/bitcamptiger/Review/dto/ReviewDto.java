package com.example.bitcamptiger.Review.dto;

import lombok.Data;

@Data
public class ReviewDto {

    private String orderNum;
    private Long storeId;
    private String userId;
    private String reviewContent;
    private int score;
}
