package com.example.bitcamptiger.Review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInputReviewDto {
    private Long vendorId;
    private Long orderId;
    private String reviewContent;
    private long reviewScore;
    private List<MultipartFile> files;
    private int likeCount;
    private int disLikeCount;
}