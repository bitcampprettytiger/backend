package com.example.bitcamptiger.Review.dto;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long reviewNum;
    private Long orderNum;
    private Long vendorId;
    private String username;
    private String reviewContent;
    private LocalDateTime reviewRegDate;
    private int reviewScore;

    public Review DtoToEntity() {
        return Review.builder()
                .reviewNum(this.reviewNum)
                .orderNum(this.orderNum)
                .vendor(Vendor.builder().id(this.vendorId).build())
                .reviewContent(this.reviewContent)
                .reviewScore(this.reviewScore)
                .reviewRegDate(this.reviewRegDate)
                .member(Member.builder().username(this.username).build())
                .build();
    }
}


