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
    private Vendor vendor;
    private Member member;
    private String reviewContent;
    private LocalDateTime reviewRegDate;
    private int reviewScore;
    private List<ReviewFileDto> reviewFileList;

    public Review DtoToEntity() {
        return Review.builder()
                .reviewNum(this.reviewNum)
                .orderNum(this.orderNum)
                .vendor(this.vendor)
                .member(this.member)
                .reviewContent(this.reviewContent)
                .reviewScore(this.reviewScore)
                .reviewRegDate(this.reviewRegDate)
                .build();
    }
}


