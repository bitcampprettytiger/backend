package com.example.bitcamptiger.Review.dto;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<ReviewFileDto> reviewFiles;

    public Review DtoToEntity() {
        Review review = Review.builder()
                .reviewNum(this.reviewNum)
                .reviewContent(this.reviewContent)
                .reviewRegDate(this.reviewRegDate)
                .reviewScore(this.reviewScore)
                .member(Member.builder().id(this.memberId).build())
                .vendor(Vendor.builder().id(this.vendorId).build())
                .orderNum(this.orderNum)
                .build();
        return review;
    }
}


