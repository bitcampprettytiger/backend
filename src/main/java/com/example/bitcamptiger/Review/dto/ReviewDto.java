package com.example.bitcamptiger.Review.dto;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long reviewId;
    private Long orderNum;
    private Long vendorId;
    private Long memberId;
    private String reviewContent;
    private LocalDateTime reviewRegDate;
    private int reviewScore;
    private int likedCount;
    private int disLikedCount;
    private ReviewFileDto reviewFile;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ReviewDto of(Review review){
        return modelMapper.map(review, ReviewDto.class);
    }

    public Review DtoToEntity() {
        Review review = Review.builder()
                .reviewNum(this.reviewId)
                .reviewContent(this.reviewContent)
                .reviewRegDate(this.reviewRegDate)
                .reviewScore(this.reviewScore)
                .likeCount(this.likedCount)
                .disLikeCount(this.disLikedCount)
                .member(Member.builder().id(this.memberId).build())
                .vendor(Vendor.builder().id(this.vendorId).build())
                .orderNum(this.orderNum)
                .build();
        return review;
    }

    public boolean isLiked() {
        return likedCount > 0;
    }

    public boolean isDisliked() {
        return disLikedCount > 0;
    }
}


