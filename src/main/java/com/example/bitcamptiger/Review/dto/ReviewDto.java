package com.example.bitcamptiger.Review.dto;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

import static junit.runner.Version.id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long reviewNum;
    private Long orderNum;
    private Long vendorId;
    private Long memberId;
    private String reviewContent;
    private LocalDateTime reviewRegDate;
    private int reviewScore;
    private List<ReviewFileDto> reviewFileList;

//    private static ModelMapper modelMapper = new ModelMapper();
//
//
//    public Review createReview() {
//        return modelMapper.map(this,Review.class);
//    }
//
//    public static ReviewDto of(Review review){
//        return modelMapper.map(review, ReviewDto.class);
//    }

//    public Review DtoToEntity() {
//        return Review.builder()
//                .reviewNum(this.reviewNum)
//                .orderNum(this.orderNum)
//                .reviewContent(this.reviewContent)
//                .reviewScore(this.reviewScore)
//                .reviewRegDate(this.reviewRegDate)
//                .build();
//    }

    public static ReviewDto entityToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewNum(review.getReviewNum());
        reviewDto.setReviewContent(review.getReviewContent());
        reviewDto.setReviewRegDate(review.getReviewRegDate());
        reviewDto.setReviewScore(review.getReviewScore());
        reviewDto.setVendorId(review.getVendor().getId());
        reviewDto.setMemberId(review.getMember().getId());
        return reviewDto;
    }
}


